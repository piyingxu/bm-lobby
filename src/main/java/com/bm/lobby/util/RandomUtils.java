package com.bm.lobby.util;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/11/4 16:38
 */

import com.bm.lobby.enums.BusinessTypeEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * 随机字符串生成工具类
 *
 * @author zhouhui
 */
public class RandomUtils {

    /**
     * 随机数字字符串最大长度
     */
    private static int MAX_LENGTH = 20;

    /**
     * 获取UUID，去掉"-"，字母转为大写
     *
     * @return
     */
    public static String getUUIDStr() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 利用当前日期时间+ 3位数字，生成随机字符串
     *
     * @author zhouhui
     */
    public static String getRandomReqMsgId() {
        String reqMsgId = getTimestampStr();
        // 获取6位随机数
        String rannum = randomStr(3);
        // 当前时间 + 系统随机生成位数
        return reqMsgId.substring(2) + rannum;
    }

    /**
     * 获取时间戳字符串
     * @return
     */
    public static String getTimestampStr(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        LocalDateTime now = LocalDateTime.now();
        String reqMsgId = now.format(formatter);
        return reqMsgId;
    }

    /**
     * 根据业务类型生成订单号
     * @param type
     * @return
     */
    public static String getBusinessOrderId(BusinessTypeEnum type) {
        String msgId = getStringRandom(8).toLowerCase();
        return  type.getId()+msgId;
    }

    /**
     * 判断订单号和业务类型是否匹配
     * @param type
     * @param orderId
     * @return
     */
    public static boolean matchBusinessTypeEnum(BusinessTypeEnum type,String orderId){
        if (orderId == null || type == null){
            return false;
        }
        if (orderId.length() < 10){
            return false;
        }
        String idType = orderId.substring(0,2);
        if (idType.equals(type.getId())){
            return true;
        }
        return false;
    }

    public static String getBusinessOrderIdWithTimeStamp(BusinessTypeEnum type){
        String msgId = getRandomReqMsgId();
        return type.getId()+msgId;
    }

    /**
     * 生成指定长度的随机数字字符串
     *
     * @param length
     * @return
     */
    public static String randomStr(int length) {
        if (length < 1) {
            return "";
        }
        if (length > MAX_LENGTH) {
            return "不支持生成超过19位随机数据";
        }
        long rannum = (long) ((Math.random() * 9 + 1) * Math.pow(10, length - 1));
        return String.valueOf(rannum);
    }

    /**
     * 生成随机字母加数字字符串（字母有大小写）
     * @param length
     * @return
     */
    public static String getStringRandom(int length) {
        StringBuilder ranStr = new StringBuilder();
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                ranStr.append ((char)(random.nextInt(26) + temp));
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                ranStr.append(String.valueOf(random.nextInt(10)));
            }
        }
        return ranStr.toString();
    }

}
