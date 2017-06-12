package com.jianglibo.wx.repository;

import com.jianglibo.wx.domain.NutchBuilderTemplate;

public interface NutchBuilderTemplateRepository extends RepositoryBase<NutchBuilderTemplate> {
	NutchBuilderTemplate findByName(String rn);
}
