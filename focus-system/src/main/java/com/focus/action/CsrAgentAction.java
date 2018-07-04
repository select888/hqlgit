package com.focus.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.focus.action.core.CoreAction;
import com.focus.entity.CsrAgent;

@Controller
@RequestMapping("/csragent/*")
public class CsrAgentAction extends CoreAction<CsrAgent,java.lang.String>{

}
