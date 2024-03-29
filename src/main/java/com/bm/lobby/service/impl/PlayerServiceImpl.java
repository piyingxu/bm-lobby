package com.bm.lobby.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.bm.lobby.config.LobbyConfiguration;
import com.bm.lobby.dao.GameConfigMapper;
import com.bm.lobby.dao.PlayerInfoMapper;
import com.bm.lobby.dao.WithdrawOrderMapper;
import com.bm.lobby.dto.base.*;
import com.bm.lobby.dto.req.*;
import com.bm.lobby.dto.res.*;
import com.bm.lobby.enums.*;
import com.bm.lobby.model.GameConfig;
import com.bm.lobby.model.PlayerInfo;
import com.bm.lobby.model.WithdrawOrder;
import com.bm.lobby.service.*;
import com.bm.lobby.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/10/30 16:41
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerServiceImpl.class);

    @Resource
    private PlayerInfoMapper playerInfoMapper;

    @Resource
    private GameConfigMapper gameConfigMapper;

    @Resource
    private WithdrawOrderMapper withdrawOrderMapper;

    @Resource
    private ThirdPartService thirdPartService;

    @Resource
    private LobbyConfiguration lobbyConfiguration;

    @Resource
    private RedisService redisService;

    @Resource
    private MagicService magicService;

    @Resource
    private CommonService commonService;

    @Override
    public RespResult<LoginRes> login(LoginReq req) {
        if (!req.getAuthType().equals(AuthTypeEnum.WECHAT.getCode())) {
            // 目前仅支持微信
            throw new ServiceException(RespLobbyCode.PARAM_ERROR);
        }
        HttpHeadReq httpHeadReq = commonService.getHeadParam();
        LoginRes loginRes = new LoginRes();
        // 获取第三方昵称和头像信息
        String authId = getThirdUserInfo(req, loginRes);
        String playerId = null;
        // 关联playerId
        PlayerInfo playerInfo = playerInfoMapper.selectByPrimaryKey(authId);
        if (playerInfo == null) {
            playerId = String.valueOf(redisService.getIncr(RedisTableEnum.AUTO_INCREMENT.getCode()));
            // 新注册用户
            PlayerInfo newPlayer = new PlayerInfo();
            newPlayer.setAppId(httpHeadReq.getAppId());
            newPlayer.setNickName(loginRes.getNickName());
            newPlayer.setHeadUrl(loginRes.getHeadUrl());
            newPlayer.setAuthId(authId);
            newPlayer.setAuthType(req.getAuthType());
            newPlayer.setClientType(httpHeadReq.getDeviceType());
            newPlayer.setClientVersion(httpHeadReq.getClientVer());
            newPlayer.setPlayerId(playerId);
            playerInfoMapper.insert(newPlayer);
        } else {
            playerId = playerInfo.getPlayerId();
        }
        String token = commonService.buildToken(httpHeadReq.getDeviceId(), playerId);
        loginRes.setToken(token);
        loginRes.setPid(playerId);
        //获取配置游戏列表
        List<GameConfig> gameListDb = gameConfigMapper.selectByAppId(httpHeadReq.getAppId());
        List<GameDto> gameList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(gameListDb)) {
            for (GameConfig game : gameListDb) {
                GameDto dto = new GameDto();
                BeanUtilsCopy.copyProperties(game, dto);
                gameList.add(dto);
            }
        }
        loginRes.setGameList(gameList);
        //设置开关
        Map<String, Boolean> funSwitch = new HashMap<>();
        funSwitch.put("Task", true);
        funSwitch.put("Challenge", false);
        loginRes.setFunSwitch(funSwitch);
        //获取金币
        long gold = magicService.getOrUpMagic(MagicEnum.GOLD, playerId, 0);
        loginRes.setGold(gold);
        return RespUtil.success(loginRes);
    }

    @Override
    public RespResult<Void> logout() {
        String pid = commonService.getCurrPid();
        HttpHeadReq httpHeadReq = commonService.getHeadParam();
        //解绑token、playerId
        redisService.delRedis(RedisTableEnum.REQUEST_TOKEN.getCode() + httpHeadReq.getDeviceId());
        redisService.delRedis(RedisTableEnum.CURR_PLAYERID.getCode() + httpHeadReq.getToken());
        return RespUtil.success(null);
    }

    @Override
    public RespResult<Long> refreshGold() {
        String pid = commonService.getCurrPid();
        long gold = magicService.getOrUpMagic(MagicEnum.GOLD, pid, 0);
        return RespUtil.success(gold);
    }

    @Override
    public RespResult<List<CheckInRes>> getCheckInStatus() {
        TreeMap<Integer, CheckInDto> checkInConfig = commonService.getCheckInConfig();
        String pid = commonService.getCurrPid();
        Map<String, Object> checkInStatusMap = redisService.hgetAll(RedisTableEnum.getCheckInKey(pid));
        if (checkInStatusMap == null || checkInStatusMap.size() == 0) {
            checkInStatusMap = new HashMap<>();
            for (CheckInDto hour : checkInConfig.values()) {
                checkInStatusMap.put(String.valueOf(hour.getHour()), String.valueOf(CheckInStatusEnum.UNREACHED_TIME.getCode()));
            }
            // 本日初始化
            redisService.putAll(RedisTableEnum.getCheckInKey(pid), checkInStatusMap, 60 * 60 * 24 * 2);
        }
        String waitTimeDb = redisService.hget(RedisTableEnum.getCheckInKey(pid), RedisTableEnum.CHECKIN_NEXTTIME.getCode());
        long waitTime = 0;
        if (StringUtils.isNotEmpty(waitTimeDb) && Long.parseLong(waitTimeDb) > System.currentTimeMillis()) {
            waitTime = (Long.parseLong(waitTimeDb) - System.currentTimeMillis()) / 1000;
        }
        List<CheckInRes> retArr = new ArrayList<>();
        for (Integer timeHour : checkInConfig.keySet()) {
            CheckInDto checkInDto = checkInConfig.get(timeHour);
            int timeHour_next = timeHour + 4;
            CheckInRes checkInRes = new CheckInRes();
            checkInRes.setHour(timeHour);
            checkInRes.setGold(checkInDto.getGold());
            checkInRes.setWaitTime(waitTime);
            String dbStatus = String.valueOf(checkInStatusMap.get(String.valueOf(timeHour)));
            if ("0".equals(dbStatus)) {
                // 未领取
                if (DateUtil.getHour() < timeHour) {
                    // 未到点
                    checkInRes.setStatus(CheckInStatusEnum.UNREACHED_TIME.getCode());
                } else if (DateUtil.getHour() >= timeHour && DateUtil.getHour() < timeHour_next) {
                    // 正点-可领
                    checkInRes.setStatus(CheckInStatusEnum.ONTIME_CAN.getCode());
                } else {
                    // 超点-可补签
                    checkInRes.setStatus(CheckInStatusEnum.REISSUE_CAN.getCode());
                    checkInRes.setGold(checkInRes.getGold() * checkInDto.getDoubleRate());
                }
            } else {
                // 可领取
                checkInRes.setStatus(CheckInStatusEnum.ALREADY_RECEIVE.getCode());
            }
            retArr.add(checkInRes);
        }
        return RespUtil.success(retArr);
    }

    @Override
    public RespResult<CheckInAwardRes> getCheckInAward(CheckInAwardReq req) {
        TreeMap<Integer, CheckInDto> checkInConfig = commonService.getCheckInConfig();
        if (!checkInConfig.containsKey(req.getHour())) {
            throw new ServiceException(RespLobbyCode.PARAM_ERROR);
        }
        String pid = commonService.getCurrPid();
        String dbStatus = redisService.hget(RedisTableEnum.getCheckInKey(pid), String.valueOf(req.getHour()));
        if (StringUtils.isEmpty(dbStatus)) {
            throw new ServiceException(RespLobbyCode.ALREADY_RECEIVE);
        }
        int currStatus = Integer.parseInt(dbStatus);
        if (currStatus == CheckInStatusEnum.ALREADY_RECEIVE.getCode()) {
            throw new ServiceException(RespLobbyCode.ALREADY_RECEIVE);
        }
        boolean repair = false;
        if (currStatus == CheckInStatusEnum.UNREACHED_TIME.getCode()) {
            int timeHour_next = req.getHour() + 4;
            if (DateUtil.getHour() < req.getHour()) {
                throw new ServiceException(RespLobbyCode.UNREACHED_TIME);
            }
            if (DateUtil.getHour() >= timeHour_next) {
                // 超点补签
                repair = true;
            }
        }
        //判断是否冷却时间内
        String waitTimeDb = redisService.hget(RedisTableEnum.getCheckInKey(pid), RedisTableEnum.CHECKIN_NEXTTIME.getCode());
        long waitTime = 0;
        if (StringUtils.isNotEmpty(waitTimeDb) && Long.parseLong(waitTimeDb) > System.currentTimeMillis()) {
            if (repair) {
                throw new ServiceException(RespLobbyCode.UNREACHED_TIME_REPAIR);
            } else {
                //非补签的情况下，补签的倒计时继续
                waitTime = (Long.parseLong(waitTimeDb) - System.currentTimeMillis()) / 1000;
            }
        }
        CheckInDto checkInDto = checkInConfig.get(req.getHour());
        long award = checkInDto.getGold();
        if (req.getWatchAd() == 1) {
            award = award * checkInDto.getDoubleRate();
        }
        redisService.hput(RedisTableEnum.getCheckInKey(pid), String.valueOf(req.getHour()), String.valueOf(CheckInStatusEnum.ALREADY_RECEIVE.getCode()));
        magicService.getOrUpMagic(MagicEnum.GOLD, pid, award);
        if (repair) {
            // 重新设置冷却时间
            waitTime = lobbyConfiguration.getCheckInWaitime();
            long nextTime = System.currentTimeMillis() + waitTime * 1000;
            redisService.hput(RedisTableEnum.getCheckInKey(pid), RedisTableEnum.CHECKIN_NEXTTIME.getCode(), String.valueOf(nextTime));
        }
        CheckInAwardRes checkInAwardRes = new CheckInAwardRes();
        checkInAwardRes.setGold(award);
        checkInAwardRes.setWaitTime(waitTime);
        return RespUtil.success(checkInAwardRes);
    }

    @Override
    public RespResult<RankRes> getRankList(RankReq req) {
        List<RankItemDTO> retList = new ArrayList<>();
        RankRes ret = new RankRes();
        RankItemDTO mine = new RankItemDTO();
        ret.setRankList(retList);
        ret.setMine(mine);
        Set<ZSetOperations.TypedTuple<String>> rank = null;
        if (req.getType() == RankEnum.GOLD.getCode()) {
            rank = redisService.reverseRangeWithScores(RedisTableEnum.RANK_GOLD.getCode(), 0, 99);
        }
        if (rank != null) {
            int top = 1;
            for (ZSetOperations.TypedTuple<String> field : rank) {
                String pid = field.getValue();
                long score = Math.round(field.getScore());
                RankItemDTO item = new RankItemDTO();
                item.setTop(top);
                item.setPid(pid);
                item.setScore(score);
                PlayerInfo playerInfo = playerInfoMapper.selectByPrimaryKey(pid);
                if (playerInfo != null) {
                    item.setNickName(playerInfo.getNickName());
                    item.setHeadUrl(playerInfo.getHeadUrl());
                }
                retList.add(item);
            }
        }
        String pid = commonService.getCurrPid();
        long top = redisService.zrank(RedisTableEnum.RANK_GOLD.getCode(), pid);
        double score = redisService.zScore(RedisTableEnum.RANK_GOLD.getCode(), pid);
        mine.setTop((int) top);
        mine.setPid(pid);
        mine.setScore(Math.round(score));
        PlayerInfo minePlayerInfo = playerInfoMapper.selectByPrimaryKey(pid);
        if (minePlayerInfo != null) {
            mine.setNickName(minePlayerInfo.getNickName());
            mine.setHeadUrl(minePlayerInfo.getHeadUrl());
        }
        return RespUtil.success(ret);
    }

    @Override
    public RespResult<PlayerInfoRes> getUserInfo() {
        String pid = commonService.getCurrPid();
        PlayerInfo playerInfo = playerInfoMapper.selectByPrimaryKey(pid);
        if (playerInfo == null) {
            throw new ServiceException(RespLobbyCode.PLAYER_UNEXIST);
        }
        PlayerInfoRes ret = new PlayerInfoRes();
        BeanUtilsCopy.copyProperties(playerInfo, ret);
        long gold = magicService.getOrUpMagic(MagicEnum.GOLD, pid, 0);
        ret.setPid(pid);
        ret.setGold(gold);
        return RespUtil.success(ret);
    }

    @Override
    public RespResult<WithdrawMainPageRes> withDrawMainPage() {
        WithdrawMainPageRes rlt = new WithdrawMainPageRes();
        String pid = commonService.getCurrPid();
        PlayerInfo playerInfo = playerInfoMapper.selectByPrimaryKey(pid);
        if (playerInfo == null) {
            throw new ServiceException(RespLobbyCode.PLAYER_UNEXIST);
        }
        long gold = magicService.getOrUpMagic(MagicEnum.GOLD, pid, 0);
        List<Integer> payChannel = new ArrayList<>();
        payChannel.add(PayChannelEnum.WECHAT.getType());
        payChannel.add(PayChannelEnum.ALIPAY.getType());
        List<WithdrawMainPageDto> withDrawAmont = new ArrayList<>();
        WithdrawMainPageDto dto = new WithdrawMainPageDto();
        dto.setAmout(1);
        dto.setCondition("需活跃度达到2点");
        WithdrawMainPageDto dto1 = new WithdrawMainPageDto();
        dto1.setAmout(5);
        dto1.setCondition("需活跃度达到10点");
        withDrawAmont.add(dto);withDrawAmont.add(dto1);
        rlt.setGold(gold);
        rlt.setActivity(2);
        rlt.setPayChannel(payChannel);
        rlt.setWithDrawAmont(withDrawAmont);
        return RespUtil.success(rlt);
    }

    @Override
    public RespResult<Void> withDraw(WithDrawReq req) {
        String pid = commonService.getCurrPid();
        WithdrawOrder order = new WithdrawOrder();
        BeanUtilsCopy.copyProperties(req, order);
        String orderId = RandomUtils.getBusinessOrderId(BusinessTypeEnum.WITHDRAW);
        if (req.getChannel() == PayChannelEnum.WECHAT.getType()) {
            orderId = "WX" + orderId;
        } else {
            orderId = "ZFB" + orderId;
        }
        order.setOrderNo(orderId);
        order.setPlayerId(pid);
        int ret = withdrawOrderMapper.insert(order);
        if (ret == 1) {
            return RespUtil.success(null);
        }
        throw new ServiceException(RespLobbyCode.WITHDRAW_FAIL);
    }

    @Override
    public RespResult<PageDto<WithdrawOrderRes>> withDrawList(PageBaseParam req) {
        PageHelper.startPage(req.getPage(), req.getLimit());
        String pid = commonService.getCurrPid();
        List<WithdrawOrder> list = withdrawOrderMapper.selectByPid(pid);
        List<WithdrawOrderRes> listRet = new ArrayList<>();
        PageInfo<WithdrawOrder> pageInfo = new PageInfo<>(list);
        PageDto<WithdrawOrderRes> target = new PageDto<>();
        BeanUtilsCopy.copyProperties(pageInfo, target);
        for (WithdrawOrder order : list) {
            WithdrawOrderRes obj = new WithdrawOrderRes();
            BeanUtilsCopy.copyProperties(order, obj);
            listRet.add(obj);
        }
        target.setList(listRet);
        return RespUtil.success(target);
    }

    @Override
    public RespResult<Integer> getActivityDay() {
        return RespUtil.success(3);
    }

    @Override
    public RespResult<WalletRes> getMyWallet() {
        String pid = commonService.getCurrPid();
        long gold = magicService.getOrUpMagic(MagicEnum.GOLD, pid, 0);


        return RespUtil.success(null);
    }

    @Override
    public RespResult<List<BroadcastRes>> getBroadcastList() {
        List<BroadcastRes> retList = new ArrayList<>();
        Map<String, Object> tempMap = redisService.hgetAll(RedisTableEnum.BROADCAST.getCode());
        if (tempMap != null) {
            for (String key : tempMap.keySet()) {
                String val = String.valueOf(tempMap.get(key));
                BroadcastRes obj = GsonUtils.fromJson2Object(val, BroadcastRes.class);
                retList.add(obj);
            }
        }
        return RespUtil.success(retList);
    }

    @Override
    public RespResult<Long> getActiveBoxAward() {
        long award = 300;
        String pid = commonService.getCurrPid();
        magicService.getOrUpMagic(MagicEnum.GOLD, pid, award);
        return RespUtil.success(award);
    }

    @Override
    public RespResult<LuckBasinRes> getLuckBasin() {
        LuckBasinRes res = new LuckBasinRes();
        List<LuckBasinItem> itemList = new ArrayList<>();
        res.setCost(100);
        res.setPlayedNum(0);
        res.setItem(itemList);

        for (int i = 0; i < 6; i++) {
            LuckBasinItem item = new LuckBasinItem();
            item.setIndex(i);
            item.setImg("https://mat1.gtimg.com/pingjs/ext2020/qqindex2018/dist/img/qq_logo_2x.png");
            item.setContent("金币" + (i + 1) * 100);
            item.setMagicId("1001");
            itemList.add(item);
        }
        return RespUtil.success(res);
    }

    @Override
    public RespResult<LuckBasinItem> doLuckBasin() {
        LuckBasinItem item = new LuckBasinItem();
        item.setIndex(3);
        item.setImg("https://mat1.gtimg.com/pingjs/ext2020/qqindex2018/dist/img/qq_logo_2x.png");
        item.setContent("金币" + 300);
        item.setMagicId("1001");
        return RespUtil.success(item);
    }

    private String getThirdUserInfo(LoginReq req, LoginRes res) {
        Map<String, Object> wxAccessToken = thirdPartService.getWxAccessToken(req.getAuthToken());
        Object wxATErrorCode = wxAccessToken.get("errcode");
        Object wxErrmsg = wxAccessToken.get("errmsg");
        if (wxATErrorCode != null) {
            throw new ServiceException(RespLobbyCode.AUTH_ERROR.getCode(), String.valueOf(wxErrmsg));
        }
        String accessToken = String.valueOf(wxAccessToken.get("access_token"));
        String openId = String.valueOf(wxAccessToken.get("openid"));
        if (StringUtil.objIsNull(openId, accessToken)) {
            throw new ServiceException(RespLobbyCode.AUTH_ERROR);
        }
        Map<String, Object> wxUserInfo = thirdPartService.getWxUserInfo(accessToken, openId);
        Object nickNameObj = wxUserInfo.get("nickname");
        Object headImgUrlObj = wxUserInfo.get("headimgurl");
        if (StringUtil.objIsNull(nickNameObj, headImgUrlObj)) {
            throw new ServiceException(RespLobbyCode.AUTH_ERROR);
        }
        res.setNickName(String.valueOf(nickNameObj));
        res.setHeadUrl(String.valueOf(headImgUrlObj));
        return openId;
    }





}
