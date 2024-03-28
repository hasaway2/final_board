package com.example.demo.security;

import java.io.*;

import org.springframework.security.access.*;
import org.springframework.security.web.access.*;
import org.springframework.stereotype.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

// AccessDeniedHandler : 인증 오류인 403에 대한 예외처리 담당
// 스프링 시큐리티의 403처리, 로그인 성공, 로그인 실패 핸들러는 스프링이 아니라 자바 기반(컨트롤러가 아니다)

@Component
public class MyAccessDenialHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if(request.getHeader("X-Requested-With")==null) {
			response.sendRedirect("/");
		} else {
			response.sendError(409);
		}
	}
}