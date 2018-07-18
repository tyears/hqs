package com.cccuu.project.service.core.generator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cccuu.project.mapper.core.generator.GeneratorTableColumnMapper;
import com.cccuu.project.model.core.generator.GeneratorTableColumn;
import com.cccuu.project.service.core.generator.GeneratorTableColumnService;
import com.cccuu.project.utils.BaseServiceImpl;

@Service("generatorTableColumnService")
public class GeneratorTableColumnServiceImpl extends BaseServiceImpl<GeneratorTableColumn> implements GeneratorTableColumnService {
	
	private GeneratorTableColumnMapper generatorTableColumnMapper;
	
	@Autowired
	public void setGeneratorTableColumnMapper(GeneratorTableColumnMapper generatorTableColumnMapper) {
		this.generatorTableColumnMapper = generatorTableColumnMapper;
		baseMapper = generatorTableColumnMapper;
	}

}
