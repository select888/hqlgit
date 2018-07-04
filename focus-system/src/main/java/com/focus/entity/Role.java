package com.focus.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.context.annotation.Description;

@Entity
@Table(name="CSR_ROLE")
public class Role implements Serializable{

	private String roleId;
	private String roleName;
	
	private String description;
	
	private List<CsrAgent> agentlist;
	
	private Set<CsrFuncP> funcplist;
	
	@Id
	@Column(name = "ROLE_ID", unique = true, nullable = false)
	@Description("角色id")
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@Column(name = "ROLE_NAME", length = 50)
	@Description("角色名")
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@ManyToMany  
    @JoinTable(name = "CSR_AGENT_ROLES", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = {  
            @JoinColumn(name = "AGENT_ID") }) 
	public List<CsrAgent> getAgentlist() {
		return agentlist;
	}
	public void setAgentlist(List<CsrAgent> agentlist) {
		this.agentlist = agentlist;
	}
	
	@ManyToMany
	@JoinTable(name = "CSR_ROLE_FUN", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = {  
            @JoinColumn(name = "FUN_ID",referencedColumnName = "TREE_ID") }) 
	public Set<CsrFuncP> getFuncplist() {
		return funcplist;
	}
	public void setFuncplist(Set<CsrFuncP> funcplist) {
		this.funcplist = funcplist;
	}
	@Column(name = "DESCRIPTION")
	@Description("角色描述")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
