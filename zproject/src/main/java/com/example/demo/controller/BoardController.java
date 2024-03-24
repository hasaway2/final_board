package com.example.demo.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import jakarta.servlet.http.*;

@Controller
public class BoardController {
	@GetMapping("/")
	public ModelAndView list(HttpSession session) {
		if(session.getAttribute("msg")==null)
			return new ModelAndView("board/list");
		String msg = (String)session.getAttribute("msg");
		session.removeAttribute("msg");
		System.out.println(msg);
		return new ModelAndView("board/list").addObject("msg", msg);
	}
}
