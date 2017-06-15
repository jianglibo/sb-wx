package com.jianglibo.wx.webapp.authorization;

public class SessionServerResponseBody {
	
	private int returnCode;
	private String returnMessage;
	private ReturnData returnData;
	
	public int getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	
	public ReturnData getReturnData() {
		return returnData;
	}
	public void setReturnData(ReturnData returnData) {
		this.returnData = returnData;
	}

	public static class ReturnData {
		
		private String id;
		private String skey;
		private WxUserInfo user_info;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getSkey() {
			return skey;
		}
		public void setSkey(String skey) {
			this.skey = skey;
		}
		public WxUserInfo getUser_info() {
			return user_info;
		}
		public void setUser_info(WxUserInfo user_info) {
			this.user_info = user_info;
		}
	}
	
}
