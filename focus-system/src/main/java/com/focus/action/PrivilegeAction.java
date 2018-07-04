package com.focus.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.focus.action.core.CoreAction;
import com.focus.entity.Privilege;

@Controller
@RequestMapping("/privilege/*")
public class PrivilegeAction extends CoreAction<Privilege,java.lang.Integer>{

}
