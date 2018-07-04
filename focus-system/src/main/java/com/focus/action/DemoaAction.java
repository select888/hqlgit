package com.focus.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.focus.action.core.CoreAction;
import com.focus.entity.Demoa;

@Controller
@RequestMapping("/demoa/*")
public class DemoaAction extends CoreAction<Demoa,java.lang.Integer>{

}
