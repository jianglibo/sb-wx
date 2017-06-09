package com.jianglibo.wx.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.NutchBuilderTemplate;
import com.jianglibo.wx.facade.NutchBuilderTemplateFacadeRepository;
import com.jianglibo.wx.repository.NutchBuilderTemplateRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class NutchBuilderTemplateFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<NutchBuilderTemplate, NutchBuilderTemplateRepository> implements NutchBuilderTemplateFacadeRepository {

	public NutchBuilderTemplateFacadeRepositoryImpl(NutchBuilderTemplateRepository jpaRepo) {
		super(jpaRepo);
	}

	@Override
	public NutchBuilderTemplate findByName(String rn) {
		return getRepository().findByName(rn);
	}
}
