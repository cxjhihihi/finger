package com.cxjhihihi.finger.utils;

import org.apache.log4j.Logger;

public class StatLogUtil {
    private static Logger logger = Logger.getLogger("statinfo");

    public static void info(String cmd, String... nameValues) {
        StringBuffer sb = new StringBuffer();
        sb.append("[cmd=").append(cmd);

        int len = nameValues.length;
        for (int i = 0; i < len; i++) {
            if (isName(i)) {
                sb.append(",").append(nameValues[i]).append("=");
            } else {
                sb.append(nameValues[i]);
            }
        }

        sb.append("]");

        logger.info(sb.toString());
    }

    private static boolean isName(int i) {
        return i % 2 == 0;
    }
}
