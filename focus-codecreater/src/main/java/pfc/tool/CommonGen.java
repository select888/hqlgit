package pfc.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.springframework.context.annotation.Description;

public class CommonGen {
	static File directory = new File("");
	static String srcpath=directory.getAbsolutePath()+"\\src\\main\\java\\com\\focus\\";
	static String pagepath=directory.getAbsolutePath()+"\\src\\main\\resources\\templates\\";
	public static void append(StringBuffer sb, String str, int num) {
		sb.append(str);
		for (int i = 0; i < num; i++) {
			sb.append("\r\n");
		}
	}

	public static String returnUstr(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String returnUstrL(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

	// key为字段名,value为字段类型
	public Map<String, String> getColumns(String classPath) {
		Map<String, String> cmap = new HashMap<String, String>();
		try {
			Class clazz = Class.forName(classPath);
			for (Field field : clazz.getDeclaredFields()) {
				Id cid = field.getAnnotation(Id.class);
				if (cid == null) {
					Column cn = field.getAnnotation(Column.class);
					if (cn != null) {
						cmap.put(field.getName(), clazz.getDeclaredField(field.getName()).getType().getName());
					}
				}
			}
			for (Method method : clazz.getDeclaredMethods()) {
				Id cid = method.getAnnotation(Id.class);
				if (cid == null) {
					Column cn = method.getAnnotation(Column.class);
					if (cn != null) {
						String cnname = transDBColumnToJavaField(cn.name());
						cmap.put(cnname, clazz.getDeclaredField(cnname).getType().getName());
					}
				}
			}
			return cmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, String> getAllColumns(String classPath) {
		Map<String, String> cmap = new HashMap<String, String>();
		try {
			Class clazz = Class.forName(classPath);
			for (Field field : clazz.getDeclaredFields()) {
				Column cn = field.getAnnotation(Column.class);
				if (cn != null) {
					cmap.put(field.getName(), clazz.getDeclaredField(field.getName()).getType().getName());
				}
			}
			for (Method method : clazz.getDeclaredMethods()) {
				Column cn = method.getAnnotation(Column.class);
				if (cn != null) {
					String cnname = transDBColumnToJavaField(cn.name());
					cmap.put(cnname, clazz.getDeclaredField(cnname).getType().getName());
				}
			}
			return cmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public Map<String, String> getAllJoinColumns(String classPath) {
		Map<String, String> cmap = new HashMap<String, String>();
		try {
			Class clazz = Class.forName(classPath);
			for (Field field : clazz.getDeclaredFields()) {
				String columntype=clazz.getDeclaredField(field.getName()).getType().getName();
				if(columntype.contains("com.focus.entity")){
					cmap.put(field.getName(), clazz.getDeclaredField(field.getName()).getType().getName());
				}
			}
			return cmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//获取所有集合字段
	public Map<String,String> getAllSetColumns(String classPath){
		Map<String, String> cmap = new HashMap<String, String>();
		try {
			Class clazz = Class.forName(classPath);
			for (Field field : clazz.getDeclaredFields()) {
				String columntype=clazz.getDeclaredField(field.getName()).getType().getName();
				if(columntype.contains("java.util.Set")){
					String setname=getGenericType(field);
					cmap.put(field.getName(), setname);
				}
			}
			return cmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 根据字段获取泛型类型
	 * */
	public String getGenericType(Field field ){
		Type genericType =field.getGenericType();
		if(genericType == null) return null;    
        // 如果是泛型参数的类型     
        if(genericType instanceof ParameterizedType){     
            ParameterizedType pt = (ParameterizedType) genericType;  
            //得到泛型里的class类型对象    
            Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];  
            return genericClazz.getName();
        }
		return null; 
	}
	// 根据字段名获取字段描述
	public String getColumnDescriptionByColumnName(String classPath, String columnname) {
		try {
			Class clazz = Class.forName(classPath);
			for (Field field : clazz.getDeclaredFields()) {
				Column cn = field.getAnnotation(Column.class);
				if (cn != null) {
					if (field.getName().equals(columnname)) {
						Description description = field.getAnnotation(Description.class);
						return description != null ? description.value() : columnname;
					}
				}
				
			}
			for (Method method : clazz.getDeclaredMethods()) {
				Column cn = method.getAnnotation(Column.class);
				if (cn != null) {
					String cnname = transDBColumnToJavaField(cn.name());
					if (cnname.equals(columnname)) {
						Description description = method.getAnnotation(Description.class);
						return description != null ? description.value() : columnname;
					}
				}

			}
			return columnname;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columnname;
	}
	//查找包含name或者desc的字段
	public String getColumnContainsStr(String classPath) {
		Map<String, String> cmap = new HashMap<String, String>();
		try {
			Class clazz = Class.forName(classPath);
			for (Field field : clazz.getDeclaredFields()) {
				Column cn = field.getAnnotation(Column.class);
				if (cn != null) {
					if (field.getName().contains("name")||field.getName().contains("desc")) {
						return field.getName();
					}
				}
				
			}
			for (Method method : clazz.getDeclaredMethods()) {
				Column cn = method.getAnnotation(Column.class);
				if (cn != null) {
					String cnname = transDBColumnToJavaField(cn.name());
					if (cnname.contains("name")||cnname.contains("desc")) {
						return cnname;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public Map<String, String> getColumnsDescription(String classPath) {
		Map<String, String> cmap = new HashMap<String, String>();
		try {
			Class clazz = Class.forName(classPath);
			for (Field field : clazz.getDeclaredFields()) {
				Id cid = field.getAnnotation(Id.class);
				if (cid == null) {
					Column cn = field.getAnnotation(Column.class);
					if (cn != null) {
						Description description = field.getAnnotation(Description.class);
						cmap.put(field.getName(), description != null ? description.value() : field.getName());

					}
				}
			}
			for (Method method : clazz.getDeclaredMethods()) {
				Id cid = method.getAnnotation(Id.class);
				if (cid == null) {
					Column cn = method.getAnnotation(Column.class);
					if (cn != null) {
						String cnname = transDBColumnToJavaField(cn.name());
						Description description = method.getAnnotation(Description.class);
						cmap.put(cnname, description != null ? description.value() : cnname);
					}
				}
			}
			return cmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getKey(String classPath) {
		try {
			Class clazz = Class.forName(classPath);
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
	
	public  String getKeyType(String classPath) {
		try {
			Class clazz = Class.forName(classPath);
			for (Field field : clazz.getDeclaredFields()) {
				Id cn = field.getAnnotation(Id.class);
				if (cn != null) {
					return clazz.getDeclaredField(field.getName()).getType().getName();
				}
			}
			for (Method method : clazz.getDeclaredMethods()) {
				Id cn = method.getAnnotation(Id.class);
				if (cn != null) {
					Column cid = method.getAnnotation(Column.class);
					if (cid != null) {
						String cnname = transDBColumnToJavaField(cid.name());
						return clazz.getDeclaredField(cnname).getType().getName();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void bufferwriterToFile(String str, String filename) {
		File file = new File(filename);
		File fileParent = file.getParentFile();  
		if(!fileParent.exists()){  
		    fileParent.mkdirs();  
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(filename));
			bw.write(str);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
}
