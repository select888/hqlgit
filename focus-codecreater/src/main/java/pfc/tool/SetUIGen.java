package pfc.tool;

public class SetUIGen  extends CommonGen  {

	public String gen(String beanname,String setname) {
		StringBuffer content =  new StringBuffer();
		append(content, "<!DOCTYPE HTML>",1);
		append(content, "<html xmlns:th=\"http://www.thymeleaf.org\">",1);
		append(content, "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />",1);
		append(content, "<div th:with=\"beanname='"+beanname.toLowerCase()+"'\"> ",1);
		append(content, "<div class=\"pageContent\">",1);
		append(content, "<div th:include=\"common::setTreeContent(${beanname},${beanname}+'/updateSet/'+${entityid}+'?refreshpath='+${beanname}+'list&fieldName="+setname+"')\"></div>",1);
		append(content, "</div>",1);
		append(content, "</div>",1);
		return content.toString();
	}

}
