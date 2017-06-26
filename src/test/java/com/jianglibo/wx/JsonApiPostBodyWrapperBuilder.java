package com.jianglibo.wx;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jianglibo.wx.JsonApiPostBodyWrapper.IdTypeWrapper;

public class JsonApiPostBodyWrapperBuilder {

	private JsonApiPostBodyWrapper body = new JsonApiPostBodyWrapper();
	
	
	public JsonApiPostBodyWrapperBuilder(String resouceName) {
		body.getData().setType(resouceName);
	}
	
	public JsonApiPostBodyWrapperBuilder addAttributePair(String an, Object av) {
		body.getData().getAttributes().put(an, av);
		return this;
	}
	
//	public JsonApiPostBodyWrapperBuilder addEmbeddedListAttr(String key, String...kvs) {
//		if (!body.getData().getAttributes().containsKey(key)) {
//			body.getData().getAttributes().put(key, new ArrayList<>());
//		}
//		List<HashMap<String, Object>> embedded =  (List<HashMap<String, Object>>) body.getData().getAttributes().get("key");
//		HashMap<String, Object> 
//		return this;
//	}
	
	public JsonApiPostBodyWrapperBuilder addRelation(String relationName, String resourceName, String...ids) {
		body.getData().getRelationships().put(relationName, new IdTypeWrapper(resourceName, ids));
		return this;
	}
	
	public JsonApiPostBodyWrapperBuilder addRelation(String relationName, String resourceName, Long...ids) {
		body.getData().getRelationships().put(relationName, new IdTypeWrapper(resourceName, ids));
		return this;
	}
	
	public JsonApiPostBodyWrapper build() {
		return body;
	}
}
