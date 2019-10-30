package com.bm.lobby.dto.base;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/10/30 14:20
 */

public class RespUtil {
    public RespUtil() {
    }

    public static RespResult<Void> success() {
        RespResult<Void> result = new RespResult(RespSystemCode.SUCCESS);
        return result;
    }

    public static <T> RespResult<T> success(T data) {
        RespResult<T> result = new RespResult(RespSystemCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static RespResult<Void> error() {
        RespResult result = new RespResult(RespSystemCode.SYSTEM_ERROR);
        return result;
    }

    public static RespResult<Void> fail(RespCode source) {
        RespResult<Void> result = new RespResult(source);
        return result;
    }

    public static <T> RespResult<T> fail(RespCode source, T data) {
        RespResult<T> result = new RespResult(source);
        result.setData(data);
        return result;
    }

    public static RespResult<Void> fail(RespCode source, String message) {
        RespResult<Void> result = new RespResult(source);
        result.setRespMsg(message);
        return result;
    }

    public static <T> RespResult<T> fail(String code, String msg) {
        RespResult<T> result = new RespResult(code, msg);
        return result;
    }

    public static boolean isSuccess(RespResult respResult) {
        if (respResult == null) {
            throw new ServiceException(RespSystemCode.SYSTEM_ERROR);
        } else {
            return RespSystemCode.SUCCESS.getCode().equals(respResult.getRespCode());
        }
    }

    public static boolean isSocketTimeOut(RespResult respResult) {
        return RespSystemCode.SOCKET_READ_TIMEOUT.getCode().equals(respResult.getRespCode());
    }

    public static boolean isFailed(RespResult respResult) {
        return !isSuccess(respResult);
    }

    public static <T> T getRespResult(RespResult<T> respResult) {
        if (respResult == null) {
            throw new ServiceException(RespSystemCode.SYSTEM_ERROR);
        } else if (RespSystemCode.SUCCESS.getCode().equals(respResult.getRespCode())) {
            return respResult.getData();
        } else {
            throw new ServiceException(respResult.getRespCode(), respResult.getRespMsg());
        }
    }

    public static <T> T getRespResultNoCheck(RespResult<T> respResult) {
        if (respResult == null) {
            throw new ServiceException(RespSystemCode.SYSTEM_ERROR);
        } else {
            return respResult.getData();
        }
    }
}
