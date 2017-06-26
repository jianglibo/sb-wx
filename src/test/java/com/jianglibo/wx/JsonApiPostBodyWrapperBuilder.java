package com.jianglibo.wx;

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
	
	public JsonApiPostBodyWrapperBuilder addRelation(String relationName, String resourceName, String...ids) {
		body.getData().getRelationships().put(relationName, new IdTypeWrapper(resourceName, ids));
		return this;
	}
	
	public JsonApiPostBodyWrapperBuilder addRelation(String relationName, String resourceName, long...ids) {
		body.getData().getRelationships().put(relationName, new IdTypeWrapper(resourceName, ids));
		return this;
	}
	
	public JsonApiPostBodyWrapper build() {
		return body;
	}
}
