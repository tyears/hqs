package com.cccuu.project.service.core.generator.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.cccuu.project.mapper.core.generator.GeneratorTableColumnMapper;
import com.cccuu.project.mapper.core.generator.GeneratorTableMapper;
import com.cccuu.project.model.core.generator.GeneratorTable;
import com.cccuu.project.model.core.generator.GeneratorTableColumn;
import com.cccuu.project.service.core.generator.GeneratorTableService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.cccuu.project.utils.FreemarkerUtils;
import com.cccuu.project.utils.ProjectGlobal;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("generatorTableService")
public class GeneratorTableServiceImpl extends BaseServiceImpl<GeneratorTable> implements GeneratorTableService {

	private GeneratorTableMapper generatorTableMapper;
	
	@Autowired
	private GeneratorTableColumnMapper generatorTableColumnMapper;
	
	@Autowired
	public void setGeneratorTableMapper(GeneratorTableMapper generatorTableMapper) {
		this.generatorTableMapper = generatorTableMapper;
		baseMapper = generatorTableMapper;
	}
	
	@Override
	@Transactional
	public PageInfo<GeneratorTable> queryListByPage(Map<String, String> params) {
		// TODO Auto-generated method stub
		//设置查询条件
		Example example = new Example(GeneratorTable.class);
		example.setOrderByClause(" id desc");
		Example.Criteria creiteria = example.createCriteria();
		String name = params.get("name");
		if(StringUtils.isNotBlank(name)){
			creiteria.andLike("name","%"+name+"%");
		}
		String code = params.get("code");
		if(StringUtils.isNotBlank(code)){
			creiteria.andLike("code","%"+code+"%");
		}
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<GeneratorTable> results = generatorTableMapper.selectByExample(example);
		
		PageInfo<GeneratorTable> pageInfo = new PageInfo<GeneratorTable>(results);
		
		return pageInfo;
	}

	@Override
	@Transactional
	public void executeCreateTable(String id) {
		// TODO Auto-generated method stub
		//查询所有字段名
		GeneratorTableColumn tempTableColumn = new GeneratorTableColumn();
		tempTableColumn.setFkId(id);
		List<GeneratorTableColumn> columns = generatorTableColumnMapper.select(tempTableColumn);
		if(columns==null||columns.size()==0){
			throw new RuntimeException("请添加字段后再生成");
		}
		
		GeneratorTable table = generatorTableMapper.selectByPrimaryKey(id);
		
		Map<String,String> map = new HashMap<String, String>();
		
		StringBuffer sqlBuffer = new StringBuffer("CREATE TABLE `"+table.getTableName()+"`(");
		
		String primaryKey = "";
		for (GeneratorTableColumn generatorTableColumn : columns) {
			String type = generatorTableColumn.getType();
			String columnName = generatorTableColumn.getName();
			Integer length = generatorTableColumn.getLength();
			if(length==null){
				if("1".equals(type)){
					length = 255;
				}else if("2".equals(type)){
					length = 11;
				}
			}
			String isPrimaryKey = generatorTableColumn.getIsPrimaryKey();
			String constraint = " NULL DEFAULT NULL";
			if(StringUtils.isNotBlank(isPrimaryKey)&&"y".equals(isPrimaryKey)){
				constraint = " NOT NULL";
				primaryKey = columnName;
			}
			String remark = generatorTableColumn.getRemark();
			remark = StringUtils.isBlank(remark) ? generatorTableColumn.getName() : remark;
			if("1".equals(type)){//varchar
				sqlBuffer.append("`"+columnName+"` varchar("+length+") CHARACTER SET utf8 COLLATE utf8_general_ci"+constraint+" COMMENT '"+remark+"' ,");
			}else if("2".equals(type)) {//int
				sqlBuffer.append("`"+columnName+"` int("+length+")"+constraint+" COMMENT '"+remark+"' ,");
			}else if("3".equals(type)){//double
				sqlBuffer.append("`"+columnName+"` double("+length+",2)"+constraint+" COMMENT '"+remark+"' ,");
			}else if("4".equals(type)){//datetime
				sqlBuffer.append("`"+columnName+"` datetime"+constraint+" COMMENT '"+remark+"' ,");
			}else if("5".equals(type)){//decimal
				sqlBuffer.append("`"+columnName+"` decimal("+length+",2)"+constraint+" COMMENT '"+remark+"' ,");
			}else{//默认varchar
				sqlBuffer.append("`"+columnName+"` varchar("+length+") CHARACTER SET utf8 COLLATE utf8_general_ci"+constraint+" COMMENT '"+remark+"' ,");
			}
		}
		sqlBuffer.append("`reserved1`  varchar(255) NULL ,");
		sqlBuffer.append("`reserved2`  varchar(255) NULL ,");
		sqlBuffer.append("`reserved3`  varchar(255) NULL ,");
		if(StringUtils.isNotBlank(primaryKey)){
			sqlBuffer.append("PRIMARY KEY (`"+primaryKey+"`),");
			sqlBuffer.append("UNIQUE INDEX `"+primaryKey+"` (`"+primaryKey+"`) USING BTREE ");
		}else{
			log.error("请添加主键字段");
			throw new RuntimeException("请添加主键字段");
		}
		sqlBuffer.append(")ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci ROW_FORMAT=DYNAMIC");
		log.info("建表语句为："+sqlBuffer.toString());
		map.put("sql", sqlBuffer.toString());
		generatorTableMapper.createTable(map);
	}

	@Override
	@Transactional
	public void createCode(Map<String, String> params) {
		String projectPath = ProjectGlobal.getProjectPath();
		//页面的system路径
		String systemPagePath = projectPath + "\\src\\main\\webapp\\WEB-INF\\pages\\system";
		String configPackage = ProjectGlobal.getConfig("package");
		configPackage = configPackage.replace(".", "\\");
		String packagePath = projectPath + "\\src\\main\\java\\" + configPackage;
		// TODO Auto-generated method stub
		/*String osName = System.getProperty("os.name");
		File fileD = new File("D:/");*/
		//仅限于windows中生成，且必须存在D盘
		
		String isDevelop = ProjectGlobal.getConfig("isDevelop");
		if(StringUtils.isNotBlank(isDevelop)&&"true".equals(isDevelop)){
		
			Map<String,Object> data = new HashMap<String,Object>();
			String id = params.get("id");
			String packageName = params.get("packageName");
			String className = params.get("className");
			String funName = params.get("funName");
			String pagePreName = params.get("pagePreName");
			
			//要生成哪些代码
			List<String> generatorTypeList = Arrays.asList(params.get("generatorType").split(","));
			
			GeneratorTable table = generatorTableMapper.selectByPrimaryKey(id);
			
			//查询所有字段名
			//设置查询条件
			Example example = new Example(GeneratorTableColumn.class);
			Example.Criteria creiteria = example.createCriteria();
			creiteria.andNotEqualTo("name", "id");
			creiteria.andEqualTo("fkId", id);
			List<GeneratorTableColumn> columns = generatorTableColumnMapper.selectByExample(example);
			data.put("packageName", packageName);
			data.put("className", className);
			data.put("tableHyName", table.getName());
			data.put("date", new Date());
			data.put("tableName", table.getTableName());
			data.put("tableColumns", columns);
			data.put("funName", funName);
			data.put("pagePreName", pagePreName);
			//生成Model
			if(generatorTypeList.contains("model")){
				FreemarkerUtils.crateFile(data, "generator/ModelTemplate.ftl", packagePath+"\\model\\"+packageName+"\\"+className+".java");
			}
			
			//生成Mapper
			if(generatorTypeList.contains("mapper")){
				FreemarkerUtils.crateFile(data, "generator/MapperTemplate.ftl", packagePath+"\\mapper\\"+packageName+"\\"+className+"Mapper.java");
			}
			//生成Service接口
			if(generatorTypeList.contains("service")){
				FreemarkerUtils.crateFile(data, "generator/ServiceTemplate.ftl", packagePath+"\\service\\"+packageName+"\\"+className+"Service.java");
			}
			//生成ServiceImpl
			if(generatorTypeList.contains("serviceImpl")){
				FreemarkerUtils.crateFile(data, "generator/ServiceImplTemplate.ftl", packagePath+"\\service\\"+packageName+"\\impl\\"+className+"ServiceImpl.java");
			}
			//生成Controller
			if(generatorTypeList.contains("controller")){
				FreemarkerUtils.crateFile(data, "generator/ControllerTemplate.ftl", packagePath+"\\controller\\"+packageName+"\\"+className+"Controller.java");							
			}
			//生成Query页面
			if(generatorTypeList.contains("queryPage")){
				FreemarkerUtils.crateFile(data, "generator/QueryPageTemplate.ftl", systemPagePath+"\\"+packageName+"\\"+pagePreName+"Query.jsp");							
			}
			//生成List页面
			if(generatorTypeList.contains("listPage")){
				FreemarkerUtils.crateFile(data, "generator/ListPageTemplate.ftl", systemPagePath+"\\"+packageName+"\\"+pagePreName+"ListData.jsp");							
			}
			//生成Edit页面
			if(generatorTypeList.contains("editPage")){
				//组合必填项字段集合
				List<GeneratorTableColumn> requiredColumns = new ArrayList<GeneratorTableColumn>();
				for (GeneratorTableColumn generatorTableColumn : columns) {
					if(StringUtils.isNotBlank(generatorTableColumn.getIsRequired())&&"y".equals(generatorTableColumn.getIsRequired())){
						requiredColumns.add(generatorTableColumn);
					}
				}
				data.put("requiredColumns", requiredColumns);
				FreemarkerUtils.crateFile(data, "generator/EditPageTemplate.ftl", systemPagePath+"\\"+packageName+"\\"+pagePreName+"Edit.jsp");							
			}
			//打开地址
    		try {
				Runtime.getRuntime().exec("cmd /c start explorer "+projectPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}else{
    		throw new RuntimeException("当前环境不允许自动生成代码");
    	}
	}
	public static void main(String[] args) {
		String[] aa = new String[]{"aa","bb","cc"};
		List<String> aaList = Arrays.asList(aa);
		System.out.println(aaList.get(1));
	}
}
