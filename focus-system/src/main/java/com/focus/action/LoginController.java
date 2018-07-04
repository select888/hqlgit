package com.focus.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focus.entity.CsrAgent;
import com.focus.entity.CsrFuncP;
import com.focus.service.CsrAgentService;
import com.focus.service.FuncService;

@Controller
public class LoginController {
	
	@Autowired
	private CsrAgentService csrAgentService;
	
	@Autowired
	private FuncService funcService;
	
	@RequestMapping(value = "/blogin", method = RequestMethod.GET)
	public String blogin() {
		return "redirect:/index.html";
	}

	@RequestMapping("/logout")
	public String logOut(HttpSession session) {
		session.removeAttribute("currentuser");
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/index.html";
	}

	@RequestMapping(value = "/blogin", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Map<String, Object> map) {
		String msg = "";
		Subject subject = SecurityUtils.getSubject();
			Session session=subject.getSession(true);
			UsernamePasswordToken token = new UsernamePasswordToken(request.getParameter("username"),
					request.getParameter("password"));
			token.setRememberMe(true);
			try {
				subject.login(token);
				} catch (UnknownAccountException ex) {	
					msg="用户不存在";
				} catch (IncorrectCredentialsException ex) {
					msg="用户密码错误";
				}catch (AuthenticationException e) {
					msg="服务器忙";
				}
			System.out.println(msg);
			if(subject.isAuthenticated()){
				CsrAgent csrAgent=csrAgentService.findByAgentId(request.getParameter("username"));
				session.setAttribute("currentuser", csrAgent);
				List<CsrFuncP> funlist=funcService.getAllFun();
				map.put("funlist", funlist);
				return "index";
			}else{
				return "redirect:/index.html";
			}
			
		
	}
}
