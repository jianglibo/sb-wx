package com.jianglibo.wx.facade;


import com.jianglibo.wx.domain.NutchBuilder;

public interface NutchBuilderFacadeRepository extends FacadeRepositoryBase<NutchBuilder> {
	NutchBuilder findByName(String rn);
}
