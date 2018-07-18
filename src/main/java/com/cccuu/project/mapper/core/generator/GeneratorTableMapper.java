package com.cccuu.project.mapper.core.generator;

import java.util.Map;

import com.cccuu.project.model.core.generator.GeneratorTable;
import com.cccuu.project.utils.BaseMapper;

public interface GeneratorTableMapper extends BaseMapper<GeneratorTable> {
	int createTable(Map<String, String> map);
}
