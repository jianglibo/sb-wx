package com.jianglibo.wx.message;

import java.time.Instant;

public abstract class WxOutMessage extends WxBody {
	
	
	
	public static abstract class WxOutMessageBuilder<T extends WxOutMessage> extends WxBodyBuilder<T> {
		
		private final long createTime;
		
		public WxOutMessageBuilder(String toUserName, String fromUserName, WxMessageType msgType) {
			super(toUserName, fromUserName, msgType);
			this.createTime = Instant.now().toEpochMilli();
		}
		
		@Override
		protected void setRequiredFields(T msg) {
			super.setRequiredFields(msg);
			msg.setCreateTime(createTime);
		}
		
	}

}
