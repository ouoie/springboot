package com.greathealth.greathealth.utils;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckCardNo {

    public static boolean checkIdCard(String cardNo) {
        if (!StringUtils.hasText(cardNo)) {
            return false;
        }

        if (cardNo.length() != 18) {
            return false;
        }

        cardNo = cardNo.toUpperCase();
        int i, n = 0;
        for (i = 0; i < 17; i++) {
            if (cardNo.charAt(i) >= '0' && cardNo.charAt(i) <= '9') {
                n++;
            } else {
                break;
            }
        }


        if (n <= 16) {
            return false;
        } else {

            String strDate = cardNo.substring(6, 14);
            // 准备第一个模板，从字符串中提取出日期数字
            String pat1 = "yyyyMMdd";
            SimpleDateFormat sdf1 = new SimpleDateFormat(pat1);
            Date d = null;
            try {
                d = sdf1.parse(strDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String r = sdf1.format(d);
            if (!r.equals(strDate)) {
                return false;
            }

            //校验算法
            int j, m, sum = 0, mod;
            int[] k = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
            for (j = 0; j < 17; j++) {
                m = Integer.parseInt(cardNo.substring(j, j + 1));
                sum += k[j] * m;
            }

            mod = sum % 11;
            String mods = "10X98765432";
            return cardNo.charAt(17) == mods.charAt(mod);
        }
    }
}
