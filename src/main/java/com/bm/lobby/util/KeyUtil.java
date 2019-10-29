package com.bm.lobby.util;

import jdk.internal.org.objectweb.asm.tree.MultiANewArrayInsnNode;

import java.util.Random;

/**
 * Created by yinqi
 * 2019-03-28 19:12
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }

}
