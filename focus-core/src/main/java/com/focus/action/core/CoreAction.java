package com.focus.action.core;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.focus.action.core.vo.Pager;
import com.focus.action.core.vo.TreeModel;
import com.focus.service.core.CommonService;
import com.focus.util.SpringUtil;

import net.sf.json.JSONArray;

@Controller
public class CoreAction<T,ID extends Serializable> extends BaseAction{
	@Resource
	protected CommonService<T, ID> commonService;
	
	
	
	
	/**
	 * author:jyl
	 * descripion:通用分页方法
	 * params:
	 * entity分页实体,附带查询条件
	 * pager分页排序页数等参数
	 * modelMap存放参数
	 * returnpath要跳转的页面
	 * */
	@RequestMapping("loadPage")
	public String loadPage(T entity,Pager pager,ModelMap modelMap,
			@RequestParam String returnpath,
			HttpServletRequest req, HttpServletResponse resp) {
		Page<T> datas = commonService.loadPage(entity,pager);
		modelMap.addAttribute("datas", datas);
		modelMap.addAttribute("entity", entity);
		modelMap.addAttribute("pager", pager);
		return returnpath;
	}
	/**
	 * author:jyl
	 * descripion:查找带回
	 *  entity分页实体,附带查询条件
	 * pager分页排序页数等参数
	 * modelMap存放参数
	 * returnpath要跳转的页面
	 * */
	@RequestMapping("listlook")
	public String listlook(T entity,Pager pager,ModelMap modelMap,
			@RequestParam String returnpath,
			HttpServletRequest req, HttpServletResponse resp) {
		Page<T> datas = commonService.loadPage(entity,pager);
		modelMap.addAttribute("datas", datas);
		modelMap.addAttribute("entity", entity);
		modelMap.addAttribute("pager", pager);
		return returnpath;
	}
	@RequestMapping("insertUI")
	public String insertUI(ModelMap modelMap,@RequestParam String returnpath,HttpServletRequest req, HttpServletResponse resp) {
		return returnpath;
	}
	/**
	 * author:jyl
	 * descripion:修改页面
	 * et要修改的实体
	 * entityid实体主键
	 * modelMap存放参数
	 * returnpath要跳转的页面
	 * */
	@RequestMapping("updateUI/{entityid}")
	public String updateUI(T et,@PathVariable ID entityid,@RequestParam String returnpath,
			ModelMap modelMap,HttpServletRequest req, HttpServletResponse resp) {
		T entity=commonService.findEntityById(entityid,et);
		modelMap.put("entity", entity);
		return returnpath;
	}
	@RequestMapping("view/{entityid}")
	public String view(T et,@PathVariable ID entityid,ModelMap modelMap,@RequestParam String returnpath,
			HttpServletRequest req, HttpServletResponse resp) {
		T entity=commonService.findEntityById(entityid,et);
		modelMap.put("entity", entity);
		return returnpath;
	}
	@RequestMapping("insert")
	public void insert(T entity,@RequestParam String refreshpath,
			HttpServletRequest request,HttpServletResponse response, Map<String, Object> map){
		commonService.saveEntity(entity);
		super.returnjson(refreshpath,request,response);
	}
	/**
	 * author:jyl
	 * descripion:加载树页面
	 * entity要加载的实体
	 * orderfiled排序字段
	 * orderdirection排序方向
	 * roottreename树的父节点名
	 * treedisplayname实体类中要显示在树的字段,树修改名的依据
	 * modelMap存放参数
	 * returnpath返回路径
	 * */
	@RequestMapping("loadTreePage")
	public String loadTreePage(T entity,@RequestParam String orderfiled,
			@RequestParam String orderdirection,@RequestParam String roottreename,
			@RequestParam String treedisplayname,
			Pager pager,ModelMap modelMap,
			@RequestParam String returnpath,
			HttpServletRequest req, HttpServletResponse resp) {
		List<TreeModel> treemodellist= new ArrayList<TreeModel>();//初始化树
		pager.setOrderDirection(orderdirection);
		pager.setOrderField(orderfiled);
		pager.setNumPerPage(10000);
		Page<T> datas = commonService.loadPage(entity,pager);
		List<T> treelist=datas.getContent();
		treemodellist.add(new TreeModel(0l, 0l, roottreename, true,true,true));
		Class clazz = entity.getClass();
		try{
			for(T tree:treelist){
				Object pid=0l;Object tname="";Object tid=0l;
				for (Field field : clazz.getDeclaredFields()) {
					String columntype = clazz.getDeclaredField(field.getName()).getType().getName();
					if(columntype.equals(tree.getClass().getTypeName())){
						T pentity=(T) getValue(tree, field.getName(), clazz);
						if(pentity!=null){
							pid=getValue(pentity, getKey(tree.getClass().getName()), clazz);
							if(pid==null)pid=0l;
						}
					}else if(treedisplayname.equals(field.getName())){
						tname=getValue(tree, field.getName(), clazz);
						if(tname==null)tname="";
					}else if(getKey(tree.getClass().getName()).equals(field.getName())){
						tid=getValue(tree, field.getName(), clazz);
						if(tid==null)tid=0l;
					}
				}
				TreeModel treemodel=new TreeModel(Long.valueOf(tid.toString()),Long.valueOf(pid.toString()), tname.toString(), 
	        						false,true,false);
	    			treemodellist.add(treemodel);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		JSONArray jo=JSONArray.fromObject(treemodellist);
		String jsonstring=jo.toString();
		modelMap.addAttribute("jsonstring", jsonstring);
		return returnpath;
	}
	/**
	 * author:jyl
	 * description:根据实体属性名获取属性值
	 * params:
	 * entity实体
	 * fieldName字段名
	 * clazz类
	 * */
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
	/**
	 * author:jyl
	 * description:根据实体属性名设置属性值
	 * params:
	 * o实体
	 * fieldName字段名
	 * fieldValue要设置的字段值
	 * clazz类
	 * */
	public void setValue(String fieldName,Class clazz,Object o,Object fieldValue){
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
			Method wM = pd.getWriteMethod();
			wM.invoke(o, fieldValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * author:jyl
	 * description:设置实体属性为集合的值
	 * */
	@RequestMapping("setSetUI/{entityid}")
	public String setSetUI(T et,@PathVariable ID entityid,@RequestParam String returnpath
			,@RequestParam String roottreename,ModelMap modelMap,
			@RequestParam String treedisplayname,@RequestParam String setsname){
		if(entityid!=null){
			T entity = commonService.findEntityById(entityid, et);
			modelMap.put("entityid", entityid);
			Class clazz = entity.getClass();
			for (Field field : clazz.getDeclaredFields()) {
				try {
					String ids="";
					field.setAccessible(true);
					if(field.getName().equals(setsname)){
						
						String setname=getGenericType(field);
						Class setclazz=Class.forName(setname);
						String key=getKey(setname);
						//获取已选集合
						Set<Object> oset=(Set<Object>) getValue(entity, field.getName(), clazz);
						for (Object obj: oset) {  
								Object keyvalue= getValue(obj, key, obj.getClass());
								ids+=keyvalue+",";
						}
						modelMap.put("ids", ids);	
						List setlist=new ArrayList();
						setlist.addAll(oset);
						
						CommonService setsevice =(CommonService) SpringUtil.getBean(returnLstr(setclazz.getSimpleName()) + "Service");
						List canselectlist = setsevice.getAll(setclazz.newInstance());
						canselectlist.removeAll(setlist);
						
						List<TreeModel> treemodellist= new ArrayList<TreeModel>();//加载可选
						treemodellist.add(new TreeModel(0l, 0l, roottreename, true,true,true));
						TreeModel treemodel;
						for(Object obj:canselectlist){
								Object pid=0l;
								String tname=(String) getValue(obj, treedisplayname, obj.getClass());
								Object keyvalue= getValue(obj, key, obj.getClass());
								for (Field setfield : setclazz.getDeclaredFields()) {
									String columntype = setclazz.getDeclaredField(setfield.getName()).getType().getName();
									if(columntype.equals(setclazz.getTypeName())){
										Object pentity=(Object) getValue(obj, setfield.getName(), obj.getClass());
										if(pentity!=null){
											pid=getValue(pentity, key, pentity.getClass());
										}
									}
								}
				        		treemodel=new TreeModel(Long.valueOf(keyvalue.toString()), Long.valueOf( pid.toString()), tname, false,true,false);
				    			treemodellist.add(treemodel);
						}
						String canselectjsonstr=JSONArray.fromObject(treemodellist).toString();
						modelMap.put("canselectjsonstr", canselectjsonstr);
						//加载已选
						treemodellist= new ArrayList<TreeModel>();
						for(Object obj:setlist){
								Object tid=getValue(obj, key, obj.getClass());
								Object tname=getValue(obj, treedisplayname, obj.getClass());
				        		treemodel=new TreeModel(Long.valueOf(tid.toString()),
				        				0l, tname.toString(), false,true,false);
				    			treemodellist.add(treemodel);
						}
						String hasselectjsonstr=JSONArray.fromObject(treemodellist).toString();
						modelMap.put("hasselectjsonstr", hasselectjsonstr);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			
			
		}
		return returnpath;
	}
	public static String returnLstr(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
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
	
	/**
	 * author:jyl
	 * description:修改树的层次关系
	 * params:
	 * entity要修改的实体
	 * jsonstring树的字符串
	 * refreshpath刷新路径
	 * */
	@RequestMapping("updateTreeOrder")
	public void updateTreeOrder(T entity,String jsonstring,
			@RequestParam String refreshpath,
			HttpServletRequest request,HttpServletResponse response){
		Map<String, List<String>> map=new HashMap<String, List<String>>();
		String menuarray[]=jsonstring.split(",");
		for(String mu:menuarray){
			String stm[]=mu.split("@");
			if(stm[0]!=null){
				T oldtree=commonService.findEntityById(Integer.valueOf(stm[0]), entity);
				try{
					if(stm[1]!=null&&!stm[1].equals("0")){
						T ctree=commonService.findEntityById(Integer.valueOf(stm[1]), entity);
						Class clazz = entity.getClass();
						for (Field field : clazz.getDeclaredFields()) {
							String columntype = clazz.getDeclaredField(field.getName()).getType().getName();
							if(columntype.equals(clazz.getTypeName())){
								setValue(field.getName(), clazz, oldtree, ctree);
								break;
							}
						}
					}else{
						Class clazz = entity.getClass();
						for (Field field : clazz.getDeclaredFields()) {
							String columntype = clazz.getDeclaredField(field.getName()).getType().getName();
							if(columntype.equals(clazz.getTypeName())){
								T newentity=null;
								setValue(field.getName(), clazz, oldtree, newentity);
								break;
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				commonService.updateEntity(oldtree);
			}
		}
		super.returnjson(refreshpath,request,response);
	}
	@RequestMapping("updateSet/{entityid}")
	public void updateSet(T entity,@RequestParam String fieldName,
			@RequestParam String ids,@RequestParam String refreshpath,
			@PathVariable ID entityid,
			HttpServletRequest request,HttpServletResponse response){
		//ID entityid=(ID) getValue(entity, getKey(entity.getClass().getName()), entity.getClass());
		entity=commonService.findEntityById(entityid, entity);
		if (ids != null && !ids.equals("")) {
			Set mset=new HashSet();
			Class clazz = entity.getClass();
			for (Field field : clazz.getDeclaredFields()) {
				field.setAccessible(true);
				if(field.getName().equals(fieldName)){
					String setname=getGenericType(field);
					try {
						Class setclazz=Class.forName(setname);
						String key=getKey(setname);
						CommonService setsevice =(CommonService) SpringUtil.getBean(returnLstr(setclazz.getSimpleName()) + "Service");
						String idsarray[]=ids.split(",");
						for (int i = 0; i < idsarray.length; i++) {
							if(idsarray[i]!=null&&!idsarray[i].equals("")){
								Object m = null;
								try {
									String keytype=getKeyType(setname);
									if(keytype.contains("Integer")){
										m = setsevice.findEntityById(Integer.valueOf(idsarray[i]), setclazz.newInstance());
									}else if(keytype.contains("Long")){
										m = setsevice.findEntityById(Long.valueOf(idsarray[i]), setclazz.newInstance());
									}else if(keytype.contains("String")){
										m = setsevice.findEntityById(idsarray[i], setclazz.newInstance());
									}
									
								} catch (Exception e) {
									e.printStackTrace();
								} 
								mset.add(m);
							}
							
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			setValue(fieldName, entity.getClass(), entity, mset);
		} else {
			setValue(fieldName, entity.getClass(), entity, null);
		}
		commonService.updateEntity(entity);
		super.returnjson(refreshpath,request,response);
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
	/**
	 * 获取主键
	 * */
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
	//修改tree名字
	@RequestMapping("updateTreename/{entityid}")
	public void updateTreename(T entity,@RequestParam String namevalue,
			@RequestParam String treename,
			@PathVariable ID entityid,@RequestParam String refreshpath,
			HttpServletRequest request,HttpServletResponse response){
			try {
				String mname=URLDecoder.decode(namevalue,"utf-8");
				entity=commonService.findEntityById(entityid,entity);
				Class clazz = entity.getClass();
				for (Field field : clazz.getDeclaredFields()) {
					if(field.getName().equals(treename)){
						setValue(field.getName(), clazz, entity, mname);
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		commonService.updateEntity(entity);
		super.returnjson(refreshpath,request,response);
	}
	@RequestMapping("update")
	public void update(T entity,@RequestParam String refreshpath,
			HttpServletRequest request,HttpServletResponse response, Map<String, Object> map){
		commonService.updateEntity(entity);
		super.returnjson(refreshpath,request,response);
	}
	@RequestMapping("del/{entityid}")
	public void del(T et,@PathVariable ID entityid,String entityids,@RequestParam String refreshpath,
			HttpServletRequest request,HttpServletResponse response, Map<String, Object> map){
		List<T> entitylist=new ArrayList<T>();
		if(entityid.equals("many")){
			for(String r:entityids.split(",")){
				T entity = commonService.findEntityById(r,et);
				entitylist.add(entity);
			}
		}else{
			T entity = commonService.findEntityById(entityid,et);
			entitylist.add(entity);
		}
		commonService.delEntityByEntityList(entitylist,et);
		super.returnjsondel(refreshpath,request,response);
	}
}
