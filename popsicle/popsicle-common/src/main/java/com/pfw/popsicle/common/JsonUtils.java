package com.pfw.popsicle.common;

import java.util.List;

import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtils {
//	public static String toJsonString(Object obj){
//		SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
////				SerializerFeature.BrowserCompatible,//浏览器和设备兼容:
////				SerializerFeature.WriteNullListAsEmpty,//list字段如果为null，输出为[]，而不是null
////				SerializerFeature.WriteNullNumberAsZero,//数值字段如果为null，输出为0，而不是null
////				SerializerFeature.WriteNullBooleanAsFalse,//Boolean字段如果为null，输出为false，而不是null
////				SerializerFeature.WriteNullStringAsEmpty,//字符类型字段如果为null，输出为""，而不是null
////				SerializerFeature.WriteClassName,//使用WriteClassName特性
////				SerializerFeature.UseSingleQuotes,//使用单引号
//				SerializerFeature.WriteDateUseDateFormat//日期格式化
//				};
//		return JSON.toJSONString(obj, features);
//	}
	
	public static String toJsonString(Object obj){
		return JSON.toJSONString(obj);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object toObject(String json, Class cls) {
		return JSON.parseObject(json,cls);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List toObjectArray(String json, Class cls) {
		return JSON.parseArray(json, cls);
	}
}
