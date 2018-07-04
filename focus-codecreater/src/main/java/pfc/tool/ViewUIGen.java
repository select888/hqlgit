package pfc.tool;

import java.util.Map;

public class ViewUIGen  extends CommonGen implements InterGen {

	@Override
	public String gen(String beanname) {
		StringBuffer content =  new StringBuffer();
		String clazzpath="com.focus.entity."+beanname;
		String beankey=getKey(clazzpath);
		append(content, "﻿<!DOCTYPE HTML>",1);
		append(content, "<html xmlns:th=\"http://www.thymeleaf.org\">",1);
		append(content, "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />",1);
		append(content, "",1);
		append(content, "<div class=\"pageContent\">",1);
		append(content, "	<form method=\"post\" action=\""+beanname.toLowerCase()+"/update?refreshpath="+beanname.toLowerCase()+"list\" class=\"pageForm required-validate\" onsubmit=\"return validateCallback(this, navTabAjaxDone);\">",1);
		append(content, "		<input type=\"hidden\" name=\""+beankey+"\" th:value=\"${entity."+beankey+"}\">",1);
		append(content, "		<div class=\"pageFormContent sortDrag\" layoutH=\"56\">",1);
		
		Map<String, String> cmap = getColumns(clazzpath);
		for(String columnname:cmap.keySet()){
			if(cmap.get(columnname).equals("java.lang.String")||cmap.get(columnname).equals("java.lang.Double")
					||cmap.get(columnname).equals("java.lang.Long")
					||cmap.get(columnname).equals("java.lang.Integer")){
				append(content, "			<p>",1);
				append(content, "				<label>"+getColumnDescriptionByColumnName(clazzpath, columnname)+"：</label>",1);
				append(content, "				<input name=\""+columnname+"\" readonly=\"readonly\" type=\"text\" maxlength=\"150\" th:value=\"${entity."+columnname+"}\"/>",1);
				append(content, "			</p>",1);
			}else if(cmap.get(columnname).equals("java.util.Date")){
				append(content, "			<p>",1);
				append(content, "				<label>"+getColumnDescriptionByColumnName(clazzpath, columnname)+"：</label>",1);
				append(content, "				<input class=\"Wdate\" name=\""+columnname+"\" type=\"text\" maxlength=\"150\" onFocus=\"WdatePicker()\" th:value=\"${entity."+columnname+"}\"/>",1);
				append(content, "			</p>",1);
			}else if(cmap.get(columnname).equals("java.sql.Timestamp")){
				append(content, "			<p>",1);
				append(content, "				<label>"+getColumnDescriptionByColumnName(clazzpath, columnname)+"：</label>",1);
				append(content, "				<input class=\"Wdate\" name=\""+columnname+"\" type=\"text\" maxlength=\"150\" onFocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\" th:value=\"${entity."+columnname+"}\"/>",1);
				append(content, "			</p>",1);
			}else if(cmap.get(columnname).equals("java.sql.Time")){
				append(content, "			<p>",1);
				append(content, "				<label>"+getColumnDescriptionByColumnName(clazzpath, columnname)+"：</label>",1);
				append(content, "				<input class=\"Wdate\" name=\""+columnname+"\" type=\"text\" maxlength=\"150\" onFocus=\"WdatePicker({dateFmt:'HH:mm:ss'})\" th:value=\"${entity."+columnname+"}\"/>",1);
				append(content, "			</p>",1);
			}
			
		}
		append(content, "			<div class=\"divider\"></div>",1);
		
		Map<String, String> joinmap = getAllJoinColumns(clazzpath);
		for(String joincolumnname:joinmap.keySet()){
				String joinkey=getKey(joinmap.get(joincolumnname));
				String joincolumn=getColumnContainsStr(joinmap.get(joincolumnname));
				if(joincolumn.equals(""))joincolumn=joinkey;
				append(content, "			<p>",1);
				append(content, "				<label>"+joincolumnname+"：</label>",1);
				append(content, "               <input name=\""+joincolumnname+"."+joincolumn+"\" type=\"text\" th:value=\"${entity."+joincolumnname+"==null}?'':${entity."+joincolumnname+"."+joincolumn+"}\"/>",1);
				append(content, "			</p>",1);
		}
		
		append(content, "		</div>",1);
		append(content, "		",1);
		append(content, "		<div class=\"formBar\">",1);
		append(content, "			<ul>",1);
		append(content, "				<li>",1);
		append(content, "					<div class=\"button\"><div class=\"buttonContent\"><button type=\"button\" class=\"close\">取消</button></div></div>",1);
		append(content, "				</li>",1);
		append(content, "			</ul>",1);
		append(content, "		</div>",1);
		append(content, "	</form>",1);
		append(content, "</div>",1);
		
		
		return content.toString();
	}

}
