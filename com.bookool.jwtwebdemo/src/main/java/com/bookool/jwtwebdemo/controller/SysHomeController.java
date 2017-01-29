package com.bookool.jwtwebdemo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookool.jwtwebdemo.JWT.*;

import net.minidev.json.JSONObject;

@Controller
public class SysHomeController {

	@RequestMapping(value = {"/", "/index"})
	public String index(HttpServletRequest req, HttpServletResponse res,
			ModelMap modelMap) {
		modelMap.put("token", req.getAttribute("data"));
		return "syshome/index";
	}

	@RequestMapping("/hello")
	public String hello(HttpServletRequest req, HttpServletResponse res,
			ModelMap modelMap) {
		modelMap.put("token", req.getAttribute("data"));
		return "syshome/hello";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest req, HttpServletResponse res) {
		Cookie cookie = new Cookie("token", "");
		cookie.setHttpOnly(true);
		// 使用https时设置Secure
		// cookie.setSecure(true);
		cookie.setPath("/");
		res.addCookie(cookie);
		return "syshome/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject login(String userName, String password,
			HttpServletResponse res) {
		JSONObject resultJSON = new JSONObject();

		// 用户名密码校验成功后，生成token返回客户端
		if ("admin".equals(userName) && "123".equals(password)) {
			// 生成token
			Map<String, Object> payload = new HashMap<String, Object>();
			Date date = new Date();
			payload.put("uid", "admin");// 用户ID
			payload.put("iat", date.getTime());// 生成时间
			payload.put("ext", date.getTime() + 1000 * 60 * 60 * 24);// 过期时间24小时
			String token = jwtpro.createToken(payload);

			resultJSON.put("success", true);
			resultJSON.put("msg", "登陆成功");
			Cookie cookie = new Cookie("token", token);
			cookie.setHttpOnly(true);
			// cookie.setSecure(true);
			cookie.setPath("/");
			res.addCookie(cookie);
		} else {
			resultJSON.put("success", false);
			resultJSON.put("msg", "用户名密码不对");
		}
		return resultJSON;
	}

}