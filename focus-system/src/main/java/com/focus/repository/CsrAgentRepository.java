package com.focus.repository;

import org.springframework.stereotype.Repository;

import com.focus.entity.CsrAgent;
import com.focus.repository.core.CommonRepository;

@Repository
public interface CsrAgentRepository extends CommonRepository<CsrAgent, java.lang.String>{

	public CsrAgent findByAgentId(String agentId);
}
