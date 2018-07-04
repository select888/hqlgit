package com.focus.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.focus.action.core.CoreAction;
import com.focus.entity.Role;

@Controller
@RequestMapping("/role/*")
public class RoleAction extends CoreAction<Role,String>{

}
