package pfc.tool;

public class RepositoryGen extends CommonGen implements InterGen{

	@Override
	public String gen(String beanname) {
		StringBuffer content=new StringBuffer();
		String clazzpath="com.focus.entity."+beanname;
		append(content, "package com.focus.repository;",1);
		append(content, "",1);
		append(content, "import org.springframework.stereotype.Repository;",1);
		append(content, "",1);
		append(content, "import com.focus.entity."+beanname+";",1);
		append(content, "import com.focus.repository.core.CommonRepository;",1);
		append(content, "",1);
		append(content, "@Repository",1);
		append(content, "public interface "+beanname+"Repository extends CommonRepository<"+beanname+", "+getKeyType(clazzpath)+">{",1);
		append(content, "",1);
		append(content, "}",1);

		return content.toString();
	}

}
