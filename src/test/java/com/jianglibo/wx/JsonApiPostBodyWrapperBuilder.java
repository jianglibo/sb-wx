package com.jianglibo.wx;


import com.jianglibo.wx.JsonApiPostBodyWrapper.CreateListBody;
import com.jianglibo.wx.JsonApiPostBodyWrapper.CreateOneBody;
import com.jianglibo.wx.JsonApiPostBodyWrapper.IdTypeListWrapper;
import com.jianglibo.wx.JsonApiPostBodyWrapper.IdTypeWrapper;

public class JsonApiPostBodyWrapperBuilder {
	
	private JsonApiPostBodyWrapperBuilder(){}
	
	public static ListWapperBuilder getListRelationBuilder(String resouceName) {
		return new ListWapperBuilder(resouceName);
	}
	
	public static OneObjectWapperBuilder getObjectRelationBuilder(String resouceName) {
		return new OneObjectWapperBuilder(resouceName);
	}
	
	public static class ListWapperBuilder {
		private JsonApiPostBodyWrapper<CreateListBody> body = new JsonApiPostBodyWrapper<>(new CreateListBody());
		
		private ListWapperBuilder(String resouceName) {
			body.getData().setType(resouceName);
		}
		
		public ListWapperBuilder addAttributePair(String an, Object av) {
			body.getData().getAttributes().put(an, av);
			return this;
		}
		
		public ListWapperBuilder addListRelation(String relationName, String resourceName, String...ids) {
			body.getData().getRelationships().put(relationName, new IdTypeListWrapper(resourceName, ids));
			return this;
		}
		
		public ListWapperBuilder addRelation(String relationName, String resourceName, Long...ids) {
			body.getData().getRelationships().put(relationName, new IdTypeListWrapper(resourceName, ids));
			return this;
		}
		
		public JsonApiPostBodyWrapper<CreateListBody> build() {
			return body;
		}
	}

	
	public static class OneObjectWapperBuilder {
		private JsonApiPostBodyWrapper<CreateOneBody> body = new JsonApiPostBodyWrapper<>(new CreateOneBody());
		
		private OneObjectWapperBuilder(String resouceName) {
			body.getData().setType(resouceName);
		}
		
		public OneObjectWapperBuilder addAttributePair(String an, Object av) {
			body.getData().getAttributes().put(an, av);
			return this;
		}
		
		public OneObjectWapperBuilder addOneRelation(String relationName, String resourceName, String id) {
			body.getData().getRelationships().put(relationName, new IdTypeWrapper(resourceName, id));
			return this;
		}
		
		public OneObjectWapperBuilder addRelation(String relationName, String resourceName, Long id) {
			body.getData().getRelationships().put(relationName, new IdTypeWrapper(resourceName, id));
			return this;
		}
		
		public JsonApiPostBodyWrapper<CreateOneBody> build() {
			return body;
		}
	}
}
