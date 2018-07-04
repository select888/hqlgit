package com.focus.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.focus.action.core.CoreAction;
import com.focus.entity.Demob;

@Controller
@RequestMapping("/demob/*")
public class DemobAction extends CoreAction<Demob,java.lang.Integer>{

}
