package com.jianglibo.wx.katharsis.dto.converter;

public interface DtoConverter<E, D> {
	
	public static enum Scenario {
		NEW, MODIFY, UNKNOWN, IN_RELATION, FIND_ONE, FIND_LIST, RELATION_LIST
	}

//	E dto2Entity(D dto);
	
	D entity2Dto(E entity, Scenario scenario);
}
