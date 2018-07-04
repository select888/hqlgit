package pfc.tool;

import java.util.Map;

public class ColumnUIGen  extends CommonGen implements InterGen {

	@Override
	public String gen(String beanname) {
		SetUIGen sug=new SetUIGen();
		StringBuffer content=new StringBuffer();
		String clazzpath="com.focus.entity."+beanname;
		String beankey=getKey(clazzpath);
		append(content, "﻿<!DOCTYPE html SYSTEM \"http://www.thymeleaf.org/dtd/xhtml1-strict-thymeleaf-4.dtd\">",1);
		append(content, "<html xmlns=\"http://www.w3.org/1999/xhtml\"",1);
		append(content, "	xmlns:th=\"http://www.thymeleaf.org\">",1);
		append(content, "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />",1);
		append(content, "<div th:fragment=\"thead\">",1);
		append(content, "	<th width=\"22\"><input type=\"checkbox\" th:group=\"entityids\" class=\"checkboxCtrl\"></th>",1);
		append(content, "	<th orderField=\""+beankey+"\" th:class=\"${pager.orderField eq '"+beankey+"'}?${pager.orderDirection}\" title=\"排序\">编号</th>",1);
		Map<String, String> cmap = getColumnsDescription(clazzpath);
		for(String columnname:cmap.keySet()){
			append(content, "	<th orderField=\""+columnname+"\" th:class=\"${pager.orderField eq '"+columnname+"'}?${pager.orderDirection}\">"+cmap.get(columnname)+"</th>",1);
		}
			
		append(content, "	<th>操作</th>",1);
		append(content, "</div>",1);
		append(content, "<div th:fragment=\"tbody(beanname)\">",1);
		append(content, "<tr th:each=\"pojo:${datas}\" th:target=\"sid_entity\"",1);
		append(content, " th:object=${pojo} th:rel=\"*{"+beankey+"}\" >",1);
		append(content, " <td><input name=\"entityids\" th:value=\"*{"+beankey+"}\" type=\"checkbox\"></td>",1);
		append(content, " <td th:text=\"*{"+beankey+"}\"></td>",1);
		for(String columnname:cmap.keySet()){
			append(content, " <td th:text=\"*{"+columnname+"}\"></td>",1);
		}
		
		
		append(content, " <td>",1);
		append(content, "	<a title=\"删除\" target=\"ajaxTodo\" th:href=\"@{${beanname}+'/del/'+*{"+beankey+"}+'?refreshpath='+${beanname}+'list'}\" class=\"btnDel\">删除</a>",1);
		append(content, "	<a title=\"编辑\"  target=\"navTab\" mask=\"true\" th:href=\"@{${beanname}+'/updateUI/'+*{"+beankey+"}+'?returnpath='+${beanname}+'/'+${beanname}+'UpdateUI'}\" class=\"btnEdit\">编辑</a>",1);
		append(content, "	<a title=\"查看\"  target=\"navTab\" mask=\"true\" th:href=\"@{${beanname}+'/view/'+*{"+beankey+"}+'?returnpath='+${beanname}+'/'+${beanname}+'view'}\">查看</a>",1);
		Map<String, String> smap =getAllSetColumns(clazzpath);
		for(String columnname:smap.keySet()){
			String cname=getColumnContainsStr(smap.get(columnname));
			append(content, "	<a title=\"设置"+columnname+
					"\"  target=\"navTab\" mask=\"true\" th:href=\"@{${beanname}+'/setSetUI/'+*{"+beankey
					+"}+'?returnpath='+${beanname}+'/'+${beanname}+'Update"+smap.get(columnname).replace("com.focus.entity.", "")
					+"UI&roottreename="+
					columnname+
					"&setsname="+columnname+"&treedisplayname="+cname+"'}\">设置"+
					columnname+"</a>",1);
			String sugcontent=sug.gen(beanname, columnname);
			bufferwriterToFile(sugcontent, pagepath+beanname.toLowerCase()+"\\"+beanname.toLowerCase()+"Update"+
			smap.get(columnname).replace("com.focus.entity.", "")+"UI.html");
		}
		append(content, " </td>",1);
		append(content, "</tr>",1);
		append(content, "</div>",1);

		append(content, "<div th:fragment=\"listlooktbody(beanname)\">",1);
		append(content, "<tr th:each=\"pojo:${datas}\" th:target=\"sid_entity\"",1);
		append(content, " th:object=${pojo} th:rel=\"*{"+beankey+"}\" >",1);
		append(content, " <td><input name=\"entityids\" th:value=\"*{"+beankey+"}\" type=\"checkbox\"></td>",1);
		append(content, " <td th:text=\"*{"+beankey+"}\"></td>",1);
		for(String columnname:cmap.keySet()){
			append(content, " <td th:text=\"*{"+columnname+"}\"></td>",1);
		}
		append(content, " <td>",1);
		String returncolumn=getColumnContainsStr(clazzpath);
		if(returncolumn.equals("")){
			returncolumn=beankey;
		}
		append(content, " <a class=\"btnSelect\" th:href=\"@{'javascript:$.bringBack({"+beankey+
				":\\''+*{"+beankey+"}+'\\',"+returncolumn+":\\''+*{"+returncolumn+"}+'\\'})'}\" multLookup=\"empItems\" title=\"查找带回\">选择</a>",1);
		append(content, " </td>",1);
		append(content, "</tr>",1);
		append(content, "</div>",1);
		
		return content.toString();
	}

}
