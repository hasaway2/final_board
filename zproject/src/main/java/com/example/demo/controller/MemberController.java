package com.example.demo.controller;

import java.security.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.support.*;

import com.example.demo.dto.*;
import com.example.demo.service.*;

import jakarta.servlet.http.*;
import jakarta.validation.*;

@Validated
@Controller
public class MemberController {
	@Autowired
	public MemberService service;
	
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
	public ModelAndView join() {
		return new ModelAndView("member/join");
	}
	
	// 2. 회원 가입
	@PreAuthorize("isAnonymous()")
	@PostMapping("/member/join")
	public ModelAndView join(@ModelAttribute @Valid MemberJoinDto dto, BindingResult bindingResult) {
		service.join(dto);
		return new ModelAndView("redirect:/member/login");
	}
	
	// 6. 내정보 보기
	@Secured("ROLE_USER")
	@GetMapping("/member/read")
	public ModelAndView read(Principal principal) {
		MemberReadDto dto = service.read(principal.getName());
		return new ModelAndView("member/read").addObject("member", dto);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ModelAndView constraintViolationExceptionHandler(ConstraintViolationException e, RedirectAttributes ra) {
		String msg = e.getConstraintViolations().stream().findFirst().get().getMessage();
		ra.addFlashAttribute("msg", msg);
		return new ModelAndView("redirect:/member/join");
	}
}
