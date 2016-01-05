package com.pfw.popsicle.front.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaiduApiManger {
	static String httpUrl = "http://apis.baidu.com/apistore/currencyservice/currency";
	
	
	public static void main(String[] args) {
		BaiduApiManger.currencyservice("CNY","USD");
	}
	public static String currencyservice(String fromCurrency,String toCurrency){
		String httpArg = "fromCurrency="+fromCurrency+"&toCurrency="+toCurrency+"&amount=1";
		String jsonResult = request(httpUrl, httpArg);
		System.out.println(jsonResult);
		
		return jsonResult;
	}
	

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  "53d738e81c730067e21f39652dbde6f7");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

}
