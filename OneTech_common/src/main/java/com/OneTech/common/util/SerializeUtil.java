package com.OneTech.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @description 对象序列化/反序列化工具类
 * @date 2019年1月2日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
public class SerializeUtil {
	
	/**
	 * 序列化对象为字节数组
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 将字节数组反序列化为对象
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
