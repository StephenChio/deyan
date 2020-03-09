package com.OneTech.common.util.CodeUtils;

import org.apache.commons.codec.digest.DigestUtils;

public class SaltUtils {
    public static String getSalt(String str){
        return  DigestUtils.md5Hex(str).substring(0,10);
    }
}
