package com.focus.repository.core;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonRepository<T,ID extends Serializable>  extends JpaRepository<T, ID> ,JpaSpecificationExecutor<T>{
	List<Object[]> queryListBySQL(String sql);
}
