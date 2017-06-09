package com.jianglibo.wx.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.SourceFile;
import com.jianglibo.wx.facade.SourceFileFacadeRepository;
import com.jianglibo.wx.repository.SourceFileRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class SourceFileFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<SourceFile, SourceFileRepository> implements SourceFileFacadeRepository{

	public SourceFileFacadeRepositoryImpl(SourceFileRepository jpaRepo) {
		super(jpaRepo);
	}
}
