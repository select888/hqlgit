package com.focus.shiro;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.focus.entity.CsrFuncP;
import com.focus.repository.CsrFuncPDao;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfiguration {

	@Resource
	private ShiroRealm myShiroRealm;
	@Resource
	private CsrFuncPDao csrFuncPDao;

	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置SecuritManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

		// <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/dwz/**", "anon");
		filterChainDefinitionMap.put("/login.html", "anon");
		filterChainDefinitionMap.put("/blogin", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		filterChainDefinitionMap.put("/**", "authc");

		List<CsrFuncP> plist = csrFuncPDao.findAll();

		for (Iterator<CsrFuncP> it = plist.iterator(); it.hasNext();) {

			CsrFuncP privilege = it.next();
			if (StringUtils.isNotEmpty(privilege.getFunUrl()) && StringUtils.isNotEmpty(privilege.getFunId())) {
				System.out.println(MessageFormat.format(PREMISSION_STRING, privilege.getFunId()));
				filterChainDefinitionMap.put(privilege.getFunUrl(),
						MessageFormat.format(PREMISSION_STRING, privilege.getFunId()));
			}
		}
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login.html");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index");
		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;

	}

	@Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

	@Bean
	public EhCacheManager ehCacheManager() {
		System.out.println("ShiroConfiguration.getEhCacheManager()");
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
		return cacheManager;
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		securityManager.setCacheManager(ehCacheManager());
		return securityManager;
	}

	@Bean
	public ShiroRealm myShiroRealm() {
		ShiroRealm myShiroRealm = new ShiroRealm();
		// myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}

	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，比如散列两次，相当于

		return hashedCredentialsMatcher;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
