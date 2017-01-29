package com.bookool.jwtwebdemo.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSONObject;

import com.bookool.jwtwebdemo.JWT.*;

public class CheckTokenJwt extends OncePerRequestFilter {
	// 不进行过滤的正则判断
	private static String excludeSet = ".*\\/(login)\\/?($|\\?.*)";

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String uri = request.getRequestURI();

		// 静态资源放行
		if (uri.matches(".*\\.\\w+($|\\?.*)")) {
			if (request.getMethod().toUpperCase().equals("GET")) {
				filterChain.doFilter(request, response);
				return;
			} else {
				response.getWriter().close();
				return;
			}
		}

		// 例外的
		if (uri.matches(excludeSet)) {
			filterChain.doFilter(request, response);
			return;
		}
		// 从cookie中获取token
		String token = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("token".equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
		}
		Map<String, Object> resultMap = jwtpro.validToken(token);
		request.setAttribute("data", resultMap);
		filterChain.doFilter(request, response);

	}

}
