package com.focus.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.focus.entity.CsrFuncP;
import com.focus.repository.CsrFuncPDao;

@Service
public class FuncService {
	@Resource
	private CsrFuncPDao csrFuncpDao;
	
	public List<CsrFuncP> getAllFun(){
		return csrFuncpDao.findAll();
	}
}
