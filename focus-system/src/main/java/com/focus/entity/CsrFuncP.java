package com.focus.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CSR_FUNC_P")
public class CsrFuncP implements Serializable{

	private static final long serialVersionUID = 4198707227711806119L;
	
	private String treeId;
	
	private String funId;
	
	private String funName;
	
	private String funUrl;
	@Id
	@Column(name = "TREE_ID", unique = true, nullable = false)
	public String getTreeId() {
		return treeId;
	}
	
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	@Column(name = "FUN_ID")
	public String getFunId() {
		return funId;
	}
	public void setFunId(String funId) {
		this.funId = funId;
	}
	@Column(name = "FUN_NAME")
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	@Column(name = "FUN_URL")
	public String getFunUrl() {
		return funUrl;
	}
	public void setFunUrl(String funUrl) {
		this.funUrl = funUrl;
	}
	
	
	
}
