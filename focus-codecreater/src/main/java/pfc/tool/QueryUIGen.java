package pfc.tool;

import java.util.Map;

public class QueryUIGen  extends CommonGen implements InterGen {

	@Override
	public String gen(String beanname) {
		StringBuffer content=new StringBuffer();
		String clazzpath="com.focus.entity."+beanname;
		append(content, "﻿<!DOCTYPE html SYSTEM \"http://www.thymeleaf.org/dtd/xhtml1-strict-thymeleaf-4.dtd\">",1);
		append(content, "<html xmlns=\"http://www.w3.org/1999/xhtml\"",1);
		append(content, "xmlns:th=\"http://www.thymeleaf.org\">",1);
		append(content, "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />",1);
		append(content, "<div th:fragment=\"query\"> ",1);
		append(content, "<!-- 查询条件 -->",1);
		
		Map<String, String> cmap = getAllColumns(clazzpath);
		int i=0;
		for(String columnname:cmap.keySet()){
			if(cmap.get(columnname).equals("java.lang.String")||cmap.get(columnname).equals("java.lang.Double")
					||cmap.get(columnname).equals("java.lang.Long")
					||cmap.get(columnname).equals("java.lang.Integer")){
				if(i%3==0){
					append(content, "<ul class=\"searchContent\">",1);
				}
				append(content, "	<li><label>"+getColumnDescriptionByColumnName(clazzpath, columnname)+"：</label> <input type=\"text\" name=\""+columnname+"\" th:value=\"${entity."+columnname+"}\" /></li>",1);
				if((i+1)%3==0){
					append(content, "</ul>",1);
				}
				i++;
			}
		}
		if(i%3!=0){
			append(content, "</ul>",1);
		}
		append(content, "",1);
		append(content, "</div>",1);
		return content.toString();
	}

}
