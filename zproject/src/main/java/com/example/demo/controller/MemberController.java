package com.example.demo.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.*;

import com.example.demo.service.*;

import jakarta.servlet.http.*;
import jakarta.validation.constraints.*;

@Controller
public class MemberController {
	@Autowired
	private MemberService service;
	
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/login")
	public ModelAndView login(HttpSession session) {
		if(session.getAttribute("msg")==null)
			return new ModelAndView("member/login");
		String message = (String)session.getAttribute("msg");
		session.removeAttribute("msg");
		return new ModelAndView("member/login").addObject("msg", message);
	}
	
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/join")
	public void join() {
	}
	
	// 1. 아이디 사용여부 확인
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/id-check")
	public ResponseEntity<String> idCheck(@NotEmpty String username) {
		Boolean result = service.idCheck(username);
		if(result)
			return ResponseEntity.ok("사용가능한 아이디입니다");
		return ResponseEntity.status(HttpStatus.CONFLICT).body("사용가능한 아이디입니다");
	}

	// 2. 회원 가입
	@PreAuthorize("isAnonymous()")
	@PostMapping("/member/join")
	public ModelAndView join(MultipartFile profile) {
		System.out.println(profile.getOriginalFilename());
		return null;
	}
}
