package com.jianglibo.wx;

import java.util.ArrayList;
import java.util.List;

import com.jianglibo.wx.katharsis.dto.Dto;

public class JsonApiListBodyWrapper {
	private List<JsonApiObItem> data;
	
	public JsonApiListBodyWrapper() {
	}
	
	public JsonApiListBodyWrapper(String type, List<? extends Dto> dtos) {
		this.data = new ArrayList<>();
		for(Dto dto : dtos) {
			this.data.add(new JsonApiObItem(type,dto.getId()));
		}
	}

	
	public JsonApiListBodyWrapper(String type, long...ids) {
		this.data = new ArrayList<>();
		for(long id : ids) {
			this.data.add(new JsonApiObItem(type,id));
		}
	}
	
	public JsonApiListBodyWrapper(String type, String...ids) {
		this.data = new ArrayList<>();
		for(String id : ids) {
			this.data.add(new JsonApiObItem(type,id));
		}
	}

	public List<JsonApiObItem> getData() {
		return data;
	}

	public void setData(List<JsonApiObItem> data) {
		this.data = data;
	}
	
	
	public static class JsonApiObItem {
		private String type;
		private String id;
		
		public JsonApiObItem(){}
		public JsonApiObItem(String type, long id){
			this.type = type;
			this.id = String.valueOf(id);
		}
		public JsonApiObItem(String type, String id){
			this.type = type;
			this.id = id;
		}
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
	}

}
