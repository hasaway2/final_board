package com.example.demo.security;

import java.io.*;

import org.springframework.security.access.*;
import org.springframework.security.web.access.*;
import org.springframework.stereotype.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

// 403 오류를 처리를 담당하는 객체

@Component
public class MyAccessDenialHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		System.out.println("==========================================");
		HttpSession session = req.getSession();
		session.setAttribute("msg", "aaaaaaaaaaaaaaaaaa");
		res.sendRedirect("/");
	}
}