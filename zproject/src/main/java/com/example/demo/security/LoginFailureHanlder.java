package com.example.demo.security;

import java.io.*;

import org.springframework.security.core.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

// 로그인 실패를 담당하는 객체

@Component
public class LoginFailureHanlder extends SimpleUrlAuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.setAttribute("msg", "로그인에 실패했습니다");
		response.sendRedirect("/member/login");
	}
}
