package com.jianglibo.wx.facade;

import com.jianglibo.wx.domain.NutchBuilderTemplate;


public interface NutchBuilderTemplateFacadeRepository extends FacadeRepositoryBase<NutchBuilderTemplate> {
	NutchBuilderTemplate findByName(String rn);
}
