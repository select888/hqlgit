package com.focus.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity 
@Table(name="PWD_STAUSERLOGIN")
public class PwdStauserlogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private String agentId;
	private String password;
	@Id
	@Column(name = "AGENT_ID", unique = true, nullable = false)
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
