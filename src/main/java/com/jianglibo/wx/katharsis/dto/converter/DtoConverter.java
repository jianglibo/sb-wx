package com.jianglibo.wx.katharsis.dto.converter;

public interface DtoConverter<E, D> {

	E dot2Entity(D dto);
	
	D entity2Dto(E entity);
	
	

}
