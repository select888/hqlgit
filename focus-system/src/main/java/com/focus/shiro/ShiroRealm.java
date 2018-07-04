package com.focus.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import com.focus.entity.CsrAgent;
import com.focus.entity.CsrFuncP;
import com.focus.entity.Role;
import com.focus.service.CsrAgentService;

@Service()
public class ShiroRealm  extends AuthorizingRealm {

	@Resource
	private CsrAgentService csrAgentService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		  
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();  
        CsrAgent csrAgent = (CsrAgent) principals.getPrimaryPrincipal();  
        for(Role role:csrAgent.getRoleList()){  
               authorizationInfo.addRole(role.getRoleName());  
               for(CsrFuncP p:role.getFuncplist()){  
                  authorizationInfo.addStringPermission(p.getFunId());  
               }  
           }  
        return authorizationInfo;  
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String agentId = (String) token.getPrincipal(); 
		CsrAgent csrAgent = csrAgentService.findByAgentId(agentId);
		if(csrAgent==null)return null;
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(  
				csrAgent, //用户名  
				csrAgent.getPwdStauserlogin().getPassword().toCharArray(), //密码  
	             getName()  //realm name  
	      );  
		return authenticationInfo;
	}

}
