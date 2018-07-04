package com.focus.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.focus.action.core.CoreAction;
import com.focus.entity.PwdStauserlogin;

@Controller
@RequestMapping("/pwdstauserlogin/*")
public class PwdStauserloginAction extends CoreAction<PwdStauserlogin,java.lang.String>{

}
