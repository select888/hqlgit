package com.focus.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CSR_AGENT")
public class CsrAgent implements Serializable{

	private String agentId;
	private String agentName;
	
	private List<Role> roleList;
	
	private PwdStauserlogin pwdStauserlogin;
	
	@Id
	@Column(name = "AGENT_ID", unique = true, nullable = false)
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	@Column(name = "AGENT_NAME", length = 50)
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	@ManyToMany(fetch = FetchType.EAGER) 
	@JoinTable(name = "CSR_AGENT_ROLES", joinColumns = { @JoinColumn(name = "AGENT_ID") },
			inverseJoinColumns = {  
            @JoinColumn(name = "ROLE_ID") }) 
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	@OneToOne
	@JoinColumn(name = "agentId")
	public PwdStauserlogin getPwdStauserlogin() {
		return pwdStauserlogin;
	}

	public void setPwdStauserlogin(PwdStauserlogin pwdStauserlogin) {
		this.pwdStauserlogin = pwdStauserlogin;
	}
	
	
	
}
