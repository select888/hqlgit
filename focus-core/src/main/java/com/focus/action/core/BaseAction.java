package com.focus.action.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.focus.action.core.vo.TreeModel;

import net.sf.json.JSONObject;

public class BaseAction{





	SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日期格式化

	SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");// 日期格式化
	
	SimpleDateFormat ssm = new SimpleDateFormat("HH:mm:ss");// 日期格式化

	SimpleDateFormat sss = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 日期格式化

	// 获得缺省的系统区域
	Locale locale = Locale.getDefault();
	// 获得资源文件
	ResourceBundle rb = ResourceBundle.getBundle("message", locale);
	public String returnI18n(String str) {
		String ss;
		try {
			ss = rb.getString(str);
		} catch (Exception e) {
			ss = str;
		}

		return ss;
	}
	public BaseAction() {
	}

	/**
	 * 获取当前主题
	 * */
	public Subject getMySubject() {
		return SecurityUtils.getSubject();
	}
	public String getKey(String classPath) {
		try {
			Class clazz = Class.forName(classPath.contains("_")?
					classPath.substring(0, classPath.indexOf("_")):classPath);
			for (Field field : clazz.getDeclaredFields()) {
				Id cn = field.getAnnotation(Id.class);
				if (cn != null) {
					return field.getName();
				}
			}
			for (Method method : clazz.getDeclaredMethods()) {
				Id cn = method.getAnnotation(Id.class);
				if (cn != null) {
					Column cid = method.getAnnotation(Column.class);
					if (cid != null) {
						String cnname = transDBColumnToJavaField(cid.name());
						return cnname;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String transDBColumnToJavaField(String dbcolumn) {
		String str[] = dbcolumn.split("_");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			if (i == 0) {
				sb.append(str[i].toLowerCase());
			} else {
				sb.append(returnUstrL(str[i]));
			}

		}
		return sb.toString();
	}
	public static String returnUstrL(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	public void returnjson(String objlist,HttpServletRequest request,HttpServletResponse response) {
		JSONObject object = new JSONObject();
		object.put("statusCode", "200");
		object.put("message", "操作成功");
		object.put("navTabId", objlist);
		object.put("rel", "");
		object.put("callbackType", "closeCurrent");
		object.put("forwardUrl", "");
		out(object,request,response);
	}

	public void returnjsondel(String objlist,HttpServletRequest request,HttpServletResponse response) {
		JSONObject object = new JSONObject();
		object.put("statusCode", "200");
		object.put("message", "操作成功");
		object.put("navTabId", objlist);
		object.put("rel", "");
		object.put("forwardUrl", "");
		out(object,request,response);
	}
	public boolean isNumeric(String str){ 
			if(str==null||str.trim().equals("")){
				return false;
			}
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}


	public Timestamp getCurrentTime() {// 获取当前时间
		return new Timestamp(System.currentTimeMillis());
	}
	public Date getNextDay(Date date,int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		date = calendar.getTime();
		return date;
	}
	public void out(JSONObject object,HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(object);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void out(String object,HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(object);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
