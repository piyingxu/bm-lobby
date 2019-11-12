package com.bm.lobby.service.auth;

import com.bm.lobby.config.LobbyConfiguration;
import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.base.RespUtil;
import com.bm.lobby.dto.req.HttpHeadReq;
import com.bm.lobby.enums.HttpParamEnum;
import com.bm.lobby.enums.RedisTableEnum;
import com.bm.lobby.enums.RespLobbyCode;
import com.bm.lobby.service.RedisService;
import com.bm.lobby.util.GetRequestJsonUtils;
import com.bm.lobby.util.GsonUtils;
import com.bm.lobby.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = "/api/*")
public class SecurityFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);

    private static final String TEST_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdUlV0xXBGTLhfKFIrB3/YTSx1M80/SzI2zDx/u+ezopxm0N07graR3+A6I9YSUuZaR5NYVLYksO3TvgH7iPBZ9Y7ZriRE56N4Xww7KA79TQvn04b5T4O7mjsrrAnEma25d/guUZVSriRYGtyPnSvF7kAKragNtMP/mZIexQuGYwIDAQAB";

    private static final String TEST_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ1SVXTFcEZMuF8oUisHf9hNLHUzzT9LMjbMPH+757OinGbQ3TuCtpHf4Doj1hJS5lpHk1hUtiSw7dO+AfuI8Fn1jtmuJETno3hfDDsoDv1NC+fThvlPg7uaOyusCcSZrbl3+C5RlVKuJFga3I+dK8XuQAqtqA20w/+Zkh7FC4ZjAgMBAAECgYB8lWX9KhmmYj0jhi/DyZWRelP5oIMqW2cxg/1o/ioX1G9c8IwyA2qHDK5p/FUbf7DRz5q8uaDgX4iHRRIW2rPSk65nZRW1lCp3rMCGxDmL5gM3l7jwuh8jg5qvchgEXbcn79wGL1NBx8LF2fq3jd2UuE9OD6ZQS5eOHWNsA5FxuQJBAPt1CDCqScoaMDsorr5Srb35DVLNSk/u5ra/vGRcYboPz9M+SBIPk9KimOkGU8d2LT9OIV3UgaNUR+52vGFOis0CQQCgKe7VBVH+sS7PVlGqkuaHrJEjxEqg2vN8tZIcTpzgwPN4IBG/XWkh+NfFOMujfUM4fzcjt3iBcqsrV/NQ6bXvAkBKzb3p/D6HSNMgRjH1nFLjOLul7jw1GzS3GMLKeFD6MMn6ZYS7Grc26ffjGmbB533+Xxe9+gG2vNPJNLlFmT/hAkBr3hH18dFZQSePiEkUIj+UXIqblhXU4+FcukSfP+q0C/9thduuEFFACgH319p+T1y4biVKrsRAGmRhmqhKdDyNAkB2OUycoj8NAHVjkADcodV8piiShlxuLUUBO0H7Pxal6UdkfXGboUUIE1gErykrEW35rPIaxFQcP7kwG/Frsahh";

    // 加密算法RSA
    public static final String KEY_ALGORITHM = "RSA";

    // 签名算法
    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(
                    "/api/lobby/player/login"
            )));

    @Resource
    private LobbyConfiguration lobbyConfiguration;

    @Resource
    private RedisService redisService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("security filter init");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        Thread.currentThread().setName(UUID.randomUUID().toString());
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        int contentLength = request.getContentLength();
        if (contentLength > 0) {
            request = new RequestWrapper((HttpServletRequest) req);
        }
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        int reqType = 0; //0-lobby,1-game
        if (path.startsWith("/api/game")) {
            reqType = 1;
        }
        // 验证请求头参数
        HttpHeadReq headInfo = getHeadInfo(request, false, reqType);
        if (headInfo == null) {
            handleResponse(response, new RespResult<>(RespLobbyCode.HEAD_PARAM_ERROR.getCode(), RespLobbyCode.HEAD_PARAM_ERROR.getMsg(), Boolean.FALSE));
            return;
        }
        boolean allowedPath = checkPath(path);
        if (allowedPath) {
            chain.doFilter(request, response);
        } else {
            //RespResult<Boolean> securityDto = RespUtil.success(Boolean.TRUE);
            RespResult<Boolean> securityDto = validate(request, reqType);
            if (!securityDto.getData()) {
                handleResponse(response, securityDto);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    private Boolean checkPath(String path) {
        if (path != null && path.length() > 2) {
            String[] paths = path.split("/");
            if (paths.length > 0) {
                String versionNum = paths[paths.length - 1];
                Matcher matcher = Pattern.compile("^v\\d+$").matcher(versionNum);
                if (matcher.find()) {
                    String curPath = "";
                    for (int i = 0; i < paths.length - 1; i++) {
                        curPath += paths[i] + "/";
                    }
                    path = curPath;
                }
            }
        }
        return hasPath(path);
    }

    public static Boolean hasPath(String path) {
        if (StringUtils.isNotBlank(path)) {
            return ALLOWED_PATHS.contains(path);
        }
        return false;
    }

    private RespResult<Boolean> validate(HttpServletRequest req, int reqType) {
        try {
            RespResult<Boolean> securityDto = RespUtil.success(Boolean.TRUE);
            HttpHeadReq headInfo = getHeadInfo(req, true, reqType);
            if (headInfo == null) {
                return new RespResult<>(RespLobbyCode.HEAD_PARAM_ERROR.getCode(), RespLobbyCode.HEAD_PARAM_ERROR.getMsg(), Boolean.FALSE);
            }
            // 验签
            if (reqType == 1) {
                String gameId = headInfo.getGameId();
                String playerId = headInfo.getPlayerId();
                String timestamp = headInfo.getTimestamp();
                String bodyJson = headInfo.getBodyJson();
                String encryData = gameId + playerId + timestamp + bodyJson;
                LOG.info("security verify reqType={}, encryData={}", reqType, encryData);
                boolean verifyFlag = verify(encryData, TEST_PUBLIC_KEY, headInfo.getSign());
                if (!verifyFlag) {
                    return new RespResult<>(RespLobbyCode.VERIFY_SIGN_FAIL.getCode(), RespLobbyCode.VERIFY_SIGN_FAIL.getMsg(), Boolean.FALSE);
                }
                return securityDto;
            }

            // 验证请求token(其中token在注册、登录的时候会赋值，默认有效期为30天，如果期间被强制下线则token会失效)
            String key1 = RedisTableEnum.REQUEST_TOKEN.getCode() + headInfo.getDeviceId();
            String token = redisService.getRedisValue(key1);
            LOG.info("security reqToken deviceId={}, token={}", headInfo.getDeviceId(), token);
            RespResult<Boolean> timeOut = new RespResult<>(RespLobbyCode.TOKEN_TIMEOUT.getCode(), RespLobbyCode.TOKEN_TIMEOUT.getMsg(), Boolean.FALSE);
            if (StringUtils.isBlank(token) || (!token.equals(headInfo.getToken()))) {
                return timeOut;
            }
            // 根据token获取当前会话的memberId(赋值同上)
            String key2 = RedisTableEnum.CURR_PLAYERID.getCode() + token;
            String playerId = redisService.getRedisValue(key2);
            if (StringUtils.isBlank(playerId)) {
                return timeOut;
            }
            // 每次请求后都会刷新token有效期
            redisService.setRedis(key1, token, lobbyConfiguration.getToken_valid_time());
            redisService.setRedis(key2, playerId, lobbyConfiguration.getToken_valid_time());
            return securityDto;
        } catch (Exception e) {
            LOG.error("security filter validate error, e={}", e);
            return new RespResult<>(RespLobbyCode.SYSTEM_BUSY.getCode(), RespLobbyCode.SYSTEM_BUSY.getMsg(), Boolean.FALSE);
        }
    }

    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey.getBytes());
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        return privateK;
    }

    public static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey pubkey = keyFactory.generatePublic(keySpec);
        return pubkey;
    }

    public static boolean verify(String encryData, String publicKey, String sign) throws Exception {
        byte[] data = encryData.getBytes();
        PublicKey publicK = getPublicKey(publicKey);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        byte[] bsign = Base64.getDecoder().decode(sign.getBytes());
        return signature.verify(bsign);
    }

    private void handleResponse(HttpServletResponse response, RespResult<Boolean> respResult) throws IOException {
        if (RespUtil.isFailed(respResult)) {
            respResult.setData(null);
        }
        String json = GsonUtils.toJson(respResult);
        LOG.info("security filter handleResponse={}", respResult);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getOutputStream().write(json.getBytes("UTF-8"));
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    private HttpHeadReq getHeadInfo(HttpServletRequest req, boolean validToken, int reqType) {
        String appId = req.getHeader(HttpParamEnum.APPID.getCode());
        String deviceId = req.getHeader(HttpParamEnum.DEVICE_ID.getCode());
        String deviceType = req.getHeader(HttpParamEnum.DEVICE_TYPE.getCode());
        String clientVer = req.getHeader(HttpParamEnum.CLIENT_VER.getCode());
        String token = req.getHeader(HttpParamEnum.TOKEN.getCode());
        String sign = req.getHeader(HttpParamEnum.REQ_SIGN.getCode());
        String game_id = req.getHeader(HttpParamEnum.GAME_ID.getCode());
        String player_id = req.getHeader(HttpParamEnum.PLAYER_ID.getCode());
        String timestamp = req.getHeader(HttpParamEnum.TIMESTAMP.getCode());
        String game_sign = req.getHeader(HttpParamEnum.GAME_SIGN.getCode());
        String ipAddress = getRemoteAddr(req);
        if (!validToken) {
            token = "unValid";
        }
        HttpHeadReq httpHeadReq = new HttpHeadReq();
        if (reqType == 0) {
            LOG.info("Request headInfo reqType={}, appId={}, deviceId={}, deviceType={}, clientVer={}, token={}, sign={}, uri={}, ipAddress={}",
                    reqType, appId, deviceId, deviceType, clientVer, token, sign, req.getRequestURI(), ipAddress);
            if (StringUtil.objIsNull(appId, deviceId, deviceType, clientVer, token)) {
                return null;
            }
            httpHeadReq.setAppId(appId);
            httpHeadReq.setDeviceId(deviceId);
            httpHeadReq.setDeviceType(deviceType);
            httpHeadReq.setClientVer(clientVer);
            httpHeadReq.setToken(token);
            httpHeadReq.setSign(sign);
        } else {
            String bodyJson = GetRequestJsonUtils.getRequestJsonString(req);
            if (!StringUtil.objIsNull(bodyJson)) {
                bodyJson = bodyJson.replaceAll("\\t|\\r|\\n", "");
            }
            LOG.info("Request headInfo reqType={}, game_id={}, player_id={}, timestamp={}, game_sign={}, bodyJson={}, uri={}, ipAddress={}",
                    reqType, game_id, player_id, timestamp, game_sign, bodyJson, req.getRequestURI(), ipAddress);
            if (StringUtil.objIsNull(game_id, player_id, timestamp, game_sign)) {
                return null;
            }
            httpHeadReq.setSign(game_sign);
            httpHeadReq.setGameId(game_id);
            httpHeadReq.setPlayerId(player_id);
            httpHeadReq.setTimestamp(timestamp);
            httpHeadReq.setBodyJson(bodyJson);
        }
        return httpHeadReq;
    }

    public String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (StringUtils.isNotEmpty(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (StringUtils.isNotEmpty(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

    @Override
    public void destroy() {
        LOG.info("security filter destroy");
    }
}
