package com.jianglibo.wx.repository;

import com.jianglibo.wx.domain.NutchBuilder;

public interface NutchBuilderRepository extends RepositoryBase<NutchBuilder> {
	NutchBuilder findByName(String rn);
}
