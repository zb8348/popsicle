package com.pfw.popsicle.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;

import com.alibaba.fastjson.JSONObject;

public class WebSocketStreamMessageInbound extends StreamInbound {

	private final String FORMAT = "%s : %s";

	// 当前连接的用户名称
	private final String user;

	public WebSocketStreamMessageInbound(String user) {
		this.user = user;
	}

	public String getUser() {
		return this.user;
	}

	private void send(String message) throws IOException {
		message = String.format(FORMAT, user, message);
		System.out.println(message);
		getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
	}

	@Override
	protected void onTextData(Reader reader) throws IOException {
		char[] chArr = new char[1024];
		int len = reader.read(chArr);
		send(String.copyValueOf(chArr, 0, len));
	}

	@Override
	protected void onClose(int status) {
		System.out.println(String.format(FORMAT, user, "closing ......"));
		super.onClose(status);
	}

	// 建立连接的触发的事件
	protected void onOpen(WsOutbound outbound) {
		// 触发连接事件，在连接池中添加连接
		JSONObject result = new JSONObject();
		result.put("type", "user_join");
		result.put("user", this.user);
		try {
			// 向当前连接发送当前在线用户的列表
			send(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onBinaryData(InputStream is) throws IOException {
	}

}
