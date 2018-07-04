package com.focus.repository.core;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
@NoRepositoryBean
public class CommonRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements CommonRepository<T, ID> {
	
	private final EntityManager entityManager;
	
	public CommonRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		entityManager = em;
	}
	public CommonRepositoryImpl(final JpaEntityInformation<T, ?> entityInformation, final EntityManager entityManager) {  
	    super(entityInformation, entityManager);  
	    this.entityManager = entityManager;  
	}
	public List<Object[]> queryListBySQL(String sql) {
		return entityManager.createNativeQuery(sql).getResultList();
	} 
}
