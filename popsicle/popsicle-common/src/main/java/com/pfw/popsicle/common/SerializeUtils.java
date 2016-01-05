package com.pfw.popsicle.common;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtils {
	private static Logger logger = LoggerFactory
			.getLogger(SerializeUtils.class);

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {
		Object result = null;
		if (isEmpty(bytes)) {
			return null;
		}
		
		try {
			result = SerializationUtils.deserialize(bytes);
		} catch (Exception ex) {
			logger.error("Failed to deserialize");
		}
		return result;
	}

	public static boolean isEmpty(byte[] data) {
		return (data == null || data.length == 0);
	}

	/**
	 * 序列化
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Serializable object) {
		byte[] result = null;
		if (object == null) {
			return new byte[0];
		}
		try {
			result = SerializationUtils.serialize(object);
		} catch (Exception ex) {
			logger.error("Failed to serialize", ex);
		}
		return result;
	}
}
