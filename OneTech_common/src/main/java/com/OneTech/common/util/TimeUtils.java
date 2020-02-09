package com.OneTech.common.util;

public class TimeUtils {
    public static String getLongDateAgo(long inputTime) {
        long time = (inputTime / 1000 / 60 );
        if (time > 60) {
            time = (inputTime / 1000 / 60 / 60);
            if (time > 24) {
                time = (inputTime / 1000 / 24 / 60 / 60);
                if (time > 7) {
                    time = (inputTime / 1000 / 24 / 60 / 60 / 7);
                    if (time > 4) {
                        time = (inputTime / 1000 / 24 / 60 / 60 / 7 / 4);
                        if (time > 12) {
                            time = (inputTime / 1000 / 24 / 60 / 60 / 7 / 4 / 12);
                            return time + "年前";
                        } else {
                            return time + "个月前";
                        }
                    } else {
                        return time + "周前";
                    }
                } else {
                    return time + "天前";
                }
            } else {
                return time + "小时前";
            }
        } else {
            return time + "分钟前";
        }
    }
}
