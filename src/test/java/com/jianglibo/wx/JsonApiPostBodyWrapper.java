package com.jianglibo.wx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonApiPostBodyWrapper<T> {
	
	private T data;
	
	public JsonApiPostBodyWrapper(T data) {
		this.data = data;
	}
	
	public static class CreateListBody {
		private String type;
		
		private Map<String, Object> attributes = new HashMap<>();
		
		private Map<String, IdTypeListWrapper> relationships = new HashMap<>();

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Map<String, Object> getAttributes() {
			return attributes;
		}

		public void setAttributes(Map<String, Object> attributes) {
			this.attributes = attributes;
		}
		
		

		public Map<String, IdTypeListWrapper> getRelationships() {
			return relationships;
		}

		public void setRelationships(Map<String, IdTypeListWrapper> relationships) {
			this.relationships = relationships;
		}
	}
	
	public static class CreateOneBody {
		private String type;
		
		private Map<String, Object> attributes = new HashMap<>();
		
		private Map<String, IdTypeWrapper> relationships = new HashMap<>();

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Map<String, Object> getAttributes() {
			return attributes;
		}

		public void setAttributes(Map<String, Object> attributes) {
			this.attributes = attributes;
		}
		
		

		public Map<String, IdTypeWrapper> getRelationships() {
			return relationships;
		}

		public void setRelationships(Map<String, IdTypeWrapper> relationships) {
			this.relationships = relationships;
		}
	}
	
	public static class IdTypeListWrapper {
		
		private List<IdType> data = new ArrayList<>();
		
		public IdTypeListWrapper(String resourceName, String...ids) {
			for(String id: ids) {
				this.data.add(new IdType(resourceName, id));
			}
			
		}
		
		public IdTypeListWrapper(String resourceName, Long...ids) {
			for(Long id : ids) {
				this.data.add(new IdType(resourceName, id));
			}
		}

		public List<IdType> getData() {
			return data;
		}

		public void setData(List<IdType> data) {
			this.data = data;
		}
	}
	
	public static class IdTypeWrapper {
		
		private IdType data;
		
		public IdTypeWrapper(String resourceName, String id) {
			this.data = new IdType(resourceName, id);
		}
		
		public IdTypeWrapper(String resourceName, Long id) {
			this.data = new IdType(resourceName, id);
		}

		public IdType getData() {
			return data;
		}

		public void setData(IdType data) {
			this.data = data;
		}
	}
	
	public static class IdType {
		private String id;
		private String type;
		
		public IdType(String type, String id) {
			super();
			this.id = id;
			this.type = type;
		}
		
		public IdType(String type, long id) {
			super();
			this.id = String.valueOf(id);
			this.type = type;
		}

		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}

