package com.focus.service.core;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.focus.action.core.vo.Pager;
import com.focus.repository.core.CommonRepository;
import com.focus.util.SpringUtil;
import com.focus.util.commonjpa.Criteria;
import com.focus.util.commonjpa.Restrictions;

@Service
public class CommonService<T, ID extends Serializable> {

	protected CommonRepository<T, ID> commonRepository;

	/**
	 * author:jyl 根据实体条件分页查询
	 */
	public Page<T> loadPage(T entity, Pager pager) {
		commonRepository = initRepoisitory(entity);
		Pageable pageable;
		if (pager.getOrderField() != null && !pager.getOrderField().equals("")) {
			Direction direction;
			if (pager.getOrderDirection().equals("asc")) {
				direction = Sort.Direction.ASC;
			} else {
				direction = Sort.Direction.DESC;
			}
			pageable = new PageRequest(pager.getPageNum(), pager.getNumPerPage(), direction, pager.getOrderField());
		} else {
			pageable = new PageRequest(pager.getPageNum(), pager.getNumPerPage());
		}
		Criteria<T> criteria = new Criteria<T>();
		try {
			Class userCla = entity.getClass();
			Field[] fs = userCla.getDeclaredFields();
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				f.setAccessible(true); // 设置些属性是可以访问的
				String type = f.getType().toString();// 得到此属性的类型
				if (type.endsWith("String")) {
					PropertyDescriptor pd;
					pd = new PropertyDescriptor(f.getName(), userCla);
					Method getMethod = pd.getReadMethod();// 获得get方法
					Object o = getMethod.invoke(entity);// 执行get方法返回一个Object
					if (o != null) {
						criteria.add(Restrictions.like(f.getName(), o.toString(), true));
					}
				} else if (type.endsWith("Integer") || type.endsWith("int") || type.endsWith("Long")
						|| type.endsWith("Double") || type.endsWith("Float")|| type.endsWith("Character")) {
					PropertyDescriptor pd;
					pd = new PropertyDescriptor(f.getName(), userCla);
					Method getMethod = pd.getReadMethod();// 获得get方法
					Object o = getMethod.invoke(entity);// 执行get方法返回一个Object
					if (o != null) {
						criteria.add(Restrictions.eq(f.getName(), o.toString(), true));
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commonRepository.findAll(criteria, pageable);
	}

	public static String returnLstr(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}
	String clazzname="";
	public CommonRepository<T, ID> initRepoisitory(T entity) {
		if(entity.getClass().getSimpleName().contains("_")){
			clazzname=entity.getClass().getSimpleName()
					.substring(0,entity.getClass().getSimpleName().indexOf("_"));
		}else{
			clazzname=entity.getClass().getSimpleName();
		}
		commonRepository = (CommonRepository<T, ID>) SpringUtil
				.getBean(returnLstr(clazzname) + "Repository");
		return commonRepository;
	}
	/**
	 * 保存对象，如果级联对象id为空，则将级联对象清空保存
	 * */
	public void saveEntity(T entity) {
		commonRepository = initRepoisitory(entity);
		Class clazz = entity.getClass();
		try {
			for (Field field : clazz.getDeclaredFields()) {
				String columntype = clazz.getDeclaredField(field.getName()).getType().getName();
				if (columntype.contains("com.focus.entity")) {
					Object o=getValue(entity, field.getName(), clazz);
					if(o!=null){
						String key=getKey(o.getClass().getName());
						Object keyvalue = getValue(o, key, o.getClass());
						if(keyvalue==null||keyvalue.equals("")){
							T newentity=null;
							setValue(field.getName(), clazz, entity, newentity);
						}
					}
				}
			}
			commonRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public Object getValue(Object entity,String fieldName,Class clazz){
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, clazz);
			Method wM = pd.getReadMethod();
			return wM.invoke(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public void setValue(String fieldName,Class clazz,Object o,Object fieldValue){
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
			Method wM = pd.getWriteMethod();
			wM.invoke(o, fieldValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public T updateEntity(T entity) {
		commonRepository = initRepoisitory(entity);
		Class clazz = entity.getClass();
		try {
			for (Field field : clazz.getDeclaredFields()) {
				String columntype = clazz.getDeclaredField(field.getName()).getType().getName();
				if (columntype.contains("com.focus.entity")) {
					 Object o=getValue(entity, field.getName(), clazz);
					if(o!=null){
						String key=getKey(o.getClass().getName());
						 Object keyvalue=getValue(o, key, o.getClass());
						if(keyvalue==null||keyvalue.equals("")){
							T newentity=null;
							setValue(field.getName(), clazz, entity, newentity);
						}
					}
					
				}
			}
			return commonRepository.saveAndFlush(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
		
	}

	/**
	 * author:jyl 根据id查询实体
	 */
	@SuppressWarnings("unchecked")
	public T findEntityById(Serializable id, T entity) {
		commonRepository = initRepoisitory(entity);
		return commonRepository.findOne((ID) id);
	}

	/**
	 * author:jyl 根据实体列表批量删除
	 */
	public void delEntityByEntityList(List<T> entitylist, T entity) {
		commonRepository = initRepoisitory(entity);
		commonRepository.deleteInBatch(entitylist);
	}

	public List<T> getAll(T entity) {
		commonRepository = initRepoisitory(entity);
		return commonRepository.findAll();
	}
	public List<Object[]> queryListBySQL(String sql) {
		return commonRepository.queryListBySQL(sql);
	}
}
