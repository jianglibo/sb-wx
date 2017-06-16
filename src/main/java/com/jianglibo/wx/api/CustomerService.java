package com.jianglibo.wx.api;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.common.hash.Hashing;

public class CustomerService {
	
	private static final String serviceUrlCommon = "https://api.weixin.qq.com/customservice/kfaccount/";
	private static final String addUrl = serviceUrlCommon + "add?access_token=";
	private static final String updateUrl = serviceUrlCommon + "update?access_token=";
	private static final String delUrl = serviceUrlCommon + "del?access_token=";
	
	private RestTemplate restTemplate = new RestTemplate();
	@Autowired
	private AccessTokenHolder tokenHolder;
	
	public boolean addCustomerAccount(String kfAccount, String nickname, String password) {
		return doService(createCa(kfAccount, nickname, password), addUrl);
	}
	
	public boolean updateCustomerAccount(String kfAccount, String nickname, String password) {
		return doService(createCa(kfAccount, nickname, password), updateUrl);
	}
	
	public boolean delCustomerAccount(String kfAccount, String nickname, String password) {
		return doService(createCa(kfAccount, nickname, password), delUrl);
	}
	
	private CustomerAccount createCa(String kfAccount, String nickname, String password) {
		return new CustomerAccount(kfAccount, nickname, Hashing.md5().hashString("sdf", StandardCharsets.ISO_8859_1).toString());
	}
	
	private boolean doService(CustomerAccount ca, String url) {
		ResponseEntity<CustomerServiceResponse> resp = restTemplate.postForEntity(url + tokenHolder.getAccessToken(), ca, CustomerServiceResponse.class);
		if (resp.getBody().getErrcode() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static class CustomerServiceResponse {
	    private int errcode;
	    private String errmsg;
		public int getErrcode() {
			return errcode;
		}
		public void setErrcode(int errcode) {
			this.errcode = errcode;
		}
		public String getErrmsg() {
			return errmsg;
		}
		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}
	    
	}
	
	
	public static class CustomerAccount {
	    private String kf_account;
	    private String nickname;
	    private String password;
	    
	    public CustomerAccount(){};
	    
		public CustomerAccount(String kf_account, String nickname, String password) {
			super();
			this.kf_account = kf_account;
			this.nickname = nickname;
			this.password = password;
		}
		
		public String getKf_account() {
			return kf_account;
		}
		public void setKf_account(String kf_account) {
			this.kf_account = kf_account;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	}

}
