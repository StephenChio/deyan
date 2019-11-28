package com.OneTech.common.util;

import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtils {
	
	private static int nums = 0;
	
	public static String getRandom32() {

		return UUID.randomUUID().toString().replace("-", "");
	}

}
