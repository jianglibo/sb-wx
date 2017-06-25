package com.jianglibo.wx.util;

public class MyJsonApiUrlBuilder {

		private StringBuilder fieldsb = new StringBuilder();
		private StringBuilder includesb = new StringBuilder();
		
		private String groupPrefix = "";
		
		private String prepend;
		
		public MyJsonApiUrlBuilder(String prepend) {
			this.prepend = prepend;
		}
		
		public MyJsonApiUrlBuilder includes(String...resourceNames) {
			includesb = new StringBuilder();
			includesb.append("include=");
			String prefix = "";
			for(String rn : resourceNames) {
				includesb.append(prefix);
				prefix = ",";
				includesb.append(rn);
			}
			return this;
		}
		
		public MyJsonApiUrlBuilder filters(String fieldName, String value) {
			fieldsb.append(groupPrefix);
			groupPrefix = "&";
			fieldsb.append("filter[")
			.append(fieldName)
			.append("]=")
			.append(value);
			return this;
		}
		
		public MyJsonApiUrlBuilder filters(String fieldName, Long value) {
			return filters(fieldName, String.valueOf(value));
		}
		
		public MyJsonApiUrlBuilder resouceFields(String resourceName, String...fieldNames) {
			fieldsb.append(groupPrefix);
			groupPrefix = "&";
			fieldsb.append("fields[")
			.append(resourceName)
			.append("]=");
			String prefix = "";
			for(String fn : fieldNames) {
				fieldsb.append(prefix);
				prefix = ",";
				fieldsb.append(fn);
			}
			return this;
		}
		
		public String build() {
			if (includesb.length() > 0) {
				return includesb.insert(0, prepend).append("&").append(fieldsb).toString();
			} else {
				return includesb.insert(0, prepend).append(fieldsb).toString();
			}
			
		}
	
}
