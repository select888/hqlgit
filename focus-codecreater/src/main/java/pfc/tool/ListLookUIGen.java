package pfc.tool;

import java.util.Map;

public class ListLookUIGen  extends CommonGen implements InterGen {

	@Override
	public String gen(String beanname) {
		StringBuffer content =  new StringBuffer();
		String clazzpath="com.focus.entity."+beanname;
		append(content, "<!DOCTYPE HTML>",1);
		append(content, "<html xmlns:th=\"http://www.thymeleaf.org\">",1);
		append(content, "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />",1);
		append(content, "<div th:with=\"beanname='"+beanname.toLowerCase()+"'\">  ",1);
		append(content, "<div th:include=\"common::pageListLookHeader(${beanname}+'/'+'listlook?returnpath='+${beanname}+'/'+${beanname}+'listlook',${beanname}+'/'+${beanname}+'query')\"></div>",1);
		append(content, "<div class=\"pageContent\">",1);
		append(content, "	<div th:include=\"common::panelListLookColumn(${beanname}+'/'+${beanname}+'column',${beanname},'136')\"></div>",1);
		append(content, "	<div th:include=\"common::listlookpager(${beanname}+'/listlook?returnpath='+${beanname}+'/'+${beanname}+'listlook')\"></div>",1);
		append(content, "</div>",1);
		append(content, "</div>",1);
		
		return content.toString();
	}

}
