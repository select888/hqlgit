package pfc.tool;

import java.util.Map;

public class InsertUIGen  extends CommonGen implements InterGen  {

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
		append(content, "	<form method=\"post\" action=\""+beanname.toLowerCase()+"/insert?refreshpath="+beanname.toLowerCase()+"list\" class=\"pageForm required-validate\" onsubmit=\"return validateCallback(this, navTabAjaxDone);\">",1);
		append(content, "		<div class=\"pageFormContent\" layoutH=\"56\">",1);
		Map<String, String> cmap = getAllColumns(clazzpath);
		for(String columnname:cmap.keySet()){
			if(cmap.get(columnname).equals("java.lang.String")||cmap.get(columnname).equals("java.lang.Double")
					||cmap.get(columnname).equals("java.lang.Long")
					||cmap.get(columnname).equals("java.lang.Integer")){
				append(content, "			<p>",1);
				append(content, "				<label>"+getColumnDescriptionByColumnName(clazzpath, columnname)+"：</label>",1);
				append(content, "				<input name=\""+columnname+"\" type=\"text\" maxlength=\"150\" value=\"\"/>",1);
				append(content, "			</p>",1);
			}else if(cmap.get(columnname).equals("java.util.Date")){
				append(content, "			<p>",1);
				append(content, "				<label>"+getColumnDescriptionByColumnName(clazzpath, columnname)+"：</label>",1);
				append(content, "				<input class=\"Wdate\" name=\""+columnname+"\" type=\"text\" maxlength=\"150\" onFocus=\"WdatePicker()\" value=\"\"/>",1);
				append(content, "			</p>",1);
			}else if(cmap.get(columnname).equals("java.sql.Timestamp")){
				append(content, "			<p>",1);
				append(content, "				<label>"+getColumnDescriptionByColumnName(clazzpath, columnname)+"：</label>",1);
				append(content, "				<input class=\"Wdate\" name=\""+columnname+"\" type=\"text\" maxlength=\"150\" onFocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\" value=\"\"/>",1);
				append(content, "			</p>",1);
			}else if(cmap.get(columnname).equals("java.sql.Time")){
				append(content, "			<p>",1);
				append(content, "				<label>"+getColumnDescriptionByColumnName(clazzpath, columnname)+"：</label>",1);
				append(content, "				<input class=\"Wdate\" name=\""+columnname+"\" type=\"text\" maxlength=\"150\" onFocus=\"WdatePicker({dateFmt:'HH:mm:ss'})\" value=\"\"/>",1);
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
				append(content, "               <input name=\""+joincolumnname.toLowerCase()+"."+joinkey+"\" type=\"hidden\"/>",1);
				append(content, "               <input name=\""+joincolumnname.toLowerCase()+"."+joincolumn+"\" type=\"text\"/>",1);
				append(content, "               <a class=\"btnLook\" href=\""+joincolumnname.toLowerCase()+"/listlook.do?returnpath="+joincolumnname.toLowerCase()
				+"/"+joincolumnname.toLowerCase()+"listlook\" lookupGroup=\""+joincolumnname.toLowerCase()+"\">查找带回</a>	",1);
				append(content, "			</p>",1);
		}

		append(content, "		</div>",1);
		append(content, "		<div class=\"formBar\">",1);
		append(content, "			<ul>",1);
		append(content, "				<li><div class=\"buttonActive\"><div class=\"buttonContent\"><button type=\"submit\">保存</button></div></div></li>",1);
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
