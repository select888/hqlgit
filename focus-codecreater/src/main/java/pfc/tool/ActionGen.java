package pfc.tool;

public class ActionGen  extends CommonGen implements InterGen {

	@Override
	public String gen(String beanname) {
		StringBuffer content=new StringBuffer();
		String clazzpath="com.focus.entity."+beanname;
		append(content, "package com.focus.action;",1);
		append(content, "",1);
		append(content, "import org.springframework.stereotype.Controller;",1);
		append(content, "import org.springframework.web.bind.annotation.RequestMapping;",1);
		append(content, "",1);
		append(content, "import com.focus.action.core.CoreAction;",1);
		append(content, "import com.focus.entity."+beanname+";",1);
		append(content, "",1);
		append(content, "@Controller",1);
		append(content, "@RequestMapping(\"/"+beanname.toLowerCase()+"/*\")",1);
		append(content, "public class "+returnUstr(beanname)+"Action extends CoreAction<"+beanname+","+getKeyType(clazzpath)+">{",1);
		append(content, "",1);
		append(content, "}",1);
		
		return content.toString();
	}

}
