package com.example.demo.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import jakarta.servlet.http.*;

@Controller
public class BoardController {
	@GetMapping("/")
	public ModelAndView list(HttpSession session) {
		return new ModelAndView("board/list");
	}
}
