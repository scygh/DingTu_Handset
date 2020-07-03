package com.scy.dingtu_handset.app.utils;

import java.math.BigDecimal;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2020/4/28 10:06
 */
public class ArithUtil {

    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();
    }
}
