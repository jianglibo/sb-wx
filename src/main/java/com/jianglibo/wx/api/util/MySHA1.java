package com.jianglibo.wx.api.util;

import java.security.MessageDigest;
import java.util.Arrays;

public class MySHA1 {

	public static String getSHA1(String...parts) throws AesException
			  {
		try {
			StringBuffer sb = new StringBuffer();
			// 字符串排序
			Arrays.sort(parts);
			for (int i = 0; i < parts.length; i++) {
				sb.append(parts[i]);
			}
			String str = sb.toString();
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();

			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ComputeSignatureError);
		}
	}
}
