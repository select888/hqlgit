package com.focus.repository.core;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

public class CommonRepositoryFactory extends JpaRepositoryFactory {

	public CommonRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
	}

	@SuppressWarnings("unchecked")
	protected JpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata, EntityManager em) {

		JpaEntityInformation<Object, Serializable> entityMetadata = mock(JpaEntityInformation.class);
		when(entityMetadata.getJavaType()).thenReturn((Class<Object>) metadata.getDomainType());
		return new CommonRepositoryImpl<Object, Serializable>(entityMetadata, em);
	}

	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

		return CommonRepositoryImpl.class;
	}
}