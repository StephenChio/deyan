package com.OneTech.common.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class BooleanUtils {

	/**
	 * 判断传入对象是否为空或值为空
	 *
	 * @param obj 需要判断是否为空的对象
	 * @return true-对象为空，false-对象非空
	 */
	/**
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		// 判断是否为空
		if (obj == null){
			return true;
		}
		// ----------------根据各种对象类型判断是否值为空--------------
		if (obj instanceof String){
			return ((String) obj).trim().equals("");
		}
		if (obj instanceof Collection) {
			Collection<?> coll = (Collection<?>) obj;
			System.out.println("coll");
			return coll.size() == 0;
		}
		if (obj instanceof Map) {
			Map<?,?> map = (Map<?,?>) obj;
			return map.size() == 0;
		}
		if (obj.getClass().isArray()){
			return Array.getLength(obj) == 0;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断传入对象是否非空或值非空
	 * @param obj 需要判断是否为空的对象
	 * @return true-对象非空，false-对象为空
	 */
	public static Boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}
	
}
