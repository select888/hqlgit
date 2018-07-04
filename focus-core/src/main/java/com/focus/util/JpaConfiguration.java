package com.focus.util;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.focus.repository.core.CommonRepositoryFactoryBean;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
@EnableJpaRepositories(basePackages="com.focus.repository.**",repositoryFactoryBeanClass = CommonRepositoryFactoryBean.class)
@EntityScan(basePackages="com.focus.entity.**")
public class JpaConfiguration {

	PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
