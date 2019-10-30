package com.bm.lobby.service.auth;

import com.bm.lobby.config.LobbyConfiguration;
import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.base.RespUtil;
import com.bm.lobby.dto.base.ServiceException;
import com.bm.lobby.dto.req.HttpHeadReq;
import com.bm.lobby.enums.HttpParamEnum;
import com.bm.lobby.enums.RedisTableEnum;
import com.bm.lobby.enums.RespLobbyCode;
import com.bm.lobby.service.RedisService;
import com.bm.lobby.util.GsonUtils;
import com.bm.lobby.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = "/api/lobby/*")
public class SecurityFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);

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
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        int contentLength = request.getContentLength();
        if (contentLength > 0) {
            request = new RequestWrapper((HttpServletRequest) req);
        }
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = checkPath(path);
        if (allowedPath) {
            chain.doFilter(request, response);
        } else {
            //RespResult<Boolean> securityDto = RespUtil.success(Boolean.TRUE);
            RespResult<Boolean> securityDto = validate(request);
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

    private RespResult<Boolean> validate(HttpServletRequest req) {
        try {
            RespResult<Boolean> securityDto = RespUtil.success(Boolean.TRUE);
            HttpHeadReq headInfo = getHeadInfo(req);
            if (headInfo == null) {
                return new RespResult<>(RespLobbyCode.PARAM_ERROR.getCode(), RespLobbyCode.PARAM_ERROR.getMsg(), Boolean.FALSE);
            }
            // 验签

            // 验证请求token(其中token在注册、登录的时候会赋值，默认有效期为30天，如果期间被强制下线则token会失效)
            String key1 = RedisTableEnum.REQUEST_TOKEN.getCode() + headInfo.getDeviceId();
            String token = redisService.getRedisValue(key1);
            LOG.info("security reqToken deviceId={}, token={}", headInfo.getDeviceId(), token);
            RespResult<Boolean> timeOut = new RespResult<>(RespLobbyCode.TOKEN_TIMEOUT.getCode(), RespLobbyCode.TOKEN_TIMEOUT.getMsg(), Boolean.FALSE);
            if (StringUtils.isBlank(token) || (!token.equals(headInfo.getToken()))) {
                return timeOut;
            }
            // 根据token获取当前会话的memberId(赋值同上)
            String key2 = RedisTableEnum.REQUEST_TOKEN.getCode() + token;
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

    private HttpHeadReq getHeadInfo(HttpServletRequest req) {
        String appId = req.getHeader(HttpParamEnum.APPID.getCode());
        String deviceId = req.getHeader(HttpParamEnum.DEVICE_ID.getCode());
        String deviceType = req.getHeader(HttpParamEnum.DEVICE_TYPE.getCode());
        String clientVer = req.getHeader(HttpParamEnum.CLIENT_VER.getCode());
        String token = req.getHeader(HttpParamEnum.TOKEN.getCode());
        String sign = req.getHeader(HttpParamEnum.REQ_SIGN.getCode());
        String ipAddress = getRemoteAddr(req);
        LOG.info("Request headInfo appId={}, deviceId={}, deviceType={}, clientVer={}, token={}, sign={}, uri={}, ipAddress={}",
                appId, deviceId, deviceType, clientVer, token, sign, req.getRequestURI(), ipAddress);
        if (StringUtil.objIsNull(appId, deviceId, deviceType, clientVer, token)) {
            return null;
        }
        HttpHeadReq httpHeadReq = new HttpHeadReq();
        httpHeadReq.setAppid(appId);
        httpHeadReq.setDeviceId(deviceId);
        httpHeadReq.setDeviceType(deviceType);
        httpHeadReq.setClientVer(clientVer);
        httpHeadReq.setToken(token);
        httpHeadReq.setSign(sign);
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
