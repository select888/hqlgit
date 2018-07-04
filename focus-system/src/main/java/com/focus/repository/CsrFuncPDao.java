package com.focus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.focus.entity.CsrFuncP;
@Repository
public interface CsrFuncPDao extends JpaRepository<CsrFuncP, String> {

}
