package com.focus.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.focus.action.core.CoreAction;
import com.focus.action.core.vo.Pager;
import com.focus.action.core.vo.TreeModel;
import com.focus.entity.Sysmenu;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/sysmenu/*")
public class SysmenuAction extends CoreAction<Sysmenu,java.lang.Integer>{
	
	
	
	
}
