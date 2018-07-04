package pfc.tool;

public class ListUIGen  extends CommonGen implements InterGen {

	@Override
	public String gen(String beanname) {
		StringBuffer content=new StringBuffer();
		append(content, "<!DOCTYPE HTML>",1);
		append(content, "<html xmlns:th=\"http://www.thymeleaf.org\">",1);
		append(content, "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />",1);
		append(content, "<div th:with=\"beanname='"+beanname.toLowerCase()+"'\">  ",1);
		append(content, "<div th:include=\"common::pageHeader(${beanname}+'/'+'loadPage?returnpath='+${beanname}+'/'+${beanname}+'list',${beanname}+'/'+${beanname}+'query')\"></div>",1);
		append(content, "<div class=\"pageContent\">",1);
		append(content, "	<div th:include=\"common::panelBar(${beanname})\"></div>",1);
		append(content, "	<div th:include=\"common::panelColumn(${beanname}+'/'+${beanname}+'column',${beanname},'136')\"></div>",1);
		append(content, "	<div th:include=\"common::pager(${beanname}+'/loadPage?returnpath='+${beanname}+'/'+${beanname}+'list')\"></div>",1);
		
		
		append(content, "</div>",1);
		append(content, "</div>",1);
		return content.toString();
	}

}
