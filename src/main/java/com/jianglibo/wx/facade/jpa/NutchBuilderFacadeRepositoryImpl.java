package com.jianglibo.wx.facade.jpa;


import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.NutchBuilder;
import com.jianglibo.wx.facade.NutchBuilderFacadeRepository;
import com.jianglibo.wx.repository.NutchBuilderRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class NutchBuilderFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<NutchBuilder, NutchBuilderRepository> implements NutchBuilderFacadeRepository {

	public NutchBuilderFacadeRepositoryImpl(NutchBuilderRepository jpaRepo) {
		super(jpaRepo);
	}

	@Override
	public NutchBuilder findByName(String rn) {
		return getRepository().findByName(rn);
	}

}
