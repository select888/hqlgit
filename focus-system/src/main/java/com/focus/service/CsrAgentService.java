package com.focus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focus.entity.CsrAgent;
import com.focus.repository.CsrAgentRepository;
import com.focus.service.core.CommonService;

@Service
public class CsrAgentService extends CommonService<CsrAgent, java.lang.String>{

	@Autowired
	private CsrAgentRepository csrAgentRepository;
	public CsrAgent findByAgentId(String agentId){
		return csrAgentRepository.findByAgentId(agentId);
	}
}
