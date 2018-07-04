package pfc.tool;

public class ServiceGen extends CommonGen implements InterGen{

	public String gen(String beanname){
		StringBuffer content=new StringBuffer();
		String clazzpath="com.focus.entity."+beanname;
		append(content, "package com.focus.service;",1);
		append(content, "",1);
		append(content, "import org.springframework.stereotype.Service;",1);
		append(content, "",1);
		append(content, "import com.focus.entity."+beanname+";",1);
		append(content, "import com.focus.service.core.CommonService;",1);
		append(content, "",1);
		append(content, "@Service",1);
		append(content, "public class "+beanname+"Service extends CommonService<"+beanname+", "+getKeyType(clazzpath)+">{",1);
		append(content, "",1);
		append(content, "}",1);


		return content.toString();
	}
}
