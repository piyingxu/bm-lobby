package com.bm.lobby.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/10/30 15:53
 */
public class StringUtil {

    public static boolean objIsNull(Object... obj) {
        if (obj == null) {
            return true;
        }
        for (Object object : obj) {
            if (object instanceof String) {
                if (StringUtils.isBlank((String) object)) {
                    return true;
                }
            } else {
                if (object == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
