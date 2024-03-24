package com.example.demo.controller;

import org.springframework.security.access.prepost.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import jakarta.servlet.http.*;

@Controller
public class MemberController {
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/login")
	public ModelAndView login(HttpSession session) {
		if(session.getAttribute("msg")==null)
			return new ModelAndView("member/login");
		String message = (String)session.getAttribute("msg");
		session.removeAttribute("msg");
		return new ModelAndView("member/login").addObject("msg", message);
	}
}
