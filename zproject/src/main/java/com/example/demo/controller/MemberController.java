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

// 스프링 예외처리를 활성화
@Validated
@Controller
public class MemberController {
	@Autowired
	private MemberService service;
	
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/login")
	public ModelAndView login() {
		return new ModelAndView("member/login");
	}
	
	
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/join")
	public ModelAndView join() {
		return new ModelAndView("member/join");
	}
	
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/find")
	public ModelAndView find() {
		return new ModelAndView("member/find");
	}
	
	@PreAuthorize("isAnonymous()")
	@PostMapping("/member/join")
	public ModelAndView join(@ModelAttribute @Valid MemberJoinDto dto, BindingResult br) {
		service.join(dto);
		return new ModelAndView("redirect:/member/login");
	}
	
	@Secured("ROLE_USER")
	@GetMapping("/member/check-password")
	public ModelAndView checkPassword(HttpSession session) {
		if(session.getAttribute("check")!=null) {
			return new ModelAndView("redirect:/member/read");
		}
		return new ModelAndView("member/check-password");
	}
	
	@Secured("ROLE_USER")
	@PostMapping("/member/check-password")
	public ModelAndView checkPassword(String password, 
			Principal principal, HttpSession session, RedirectAttributes ra) {
		Boolean result = service.checkPassword(password, principal.getName());
		if(result==true) {
			session.setAttribute("check", true);
			return new ModelAndView("redirect:/member/read");
		}
		ra.addFlashAttribute("msg", "비밀번호를 확인하세요");
		return new ModelAndView("redirect:/member/check-password");
	}

	@Secured("ROLE_USER")
	@GetMapping("/member/read")
	public ModelAndView read(Principal principal, HttpSession session) {
		// 비밀번호를 확인하면 세션에 check 값을 true로 설정
		if(session.getAttribute("check")==null) {
			return new ModelAndView("redirect:/member/check-password");
		}
		MemberReadDto member = service.read(principal.getName());
		return new ModelAndView("member/read").addObject("member", member);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ModelAndView CVEHandler2(ConstraintViolationException e, 
			RedirectAttributes ra) {
		ra.addFlashAttribute("msg", "프로필 사진을 제외한 모든 값은 필수입니다");
		return new ModelAndView("redirect:/member/join");
	}
	
	@Secured("ROLE_USER")
	@PostMapping("/member/delete")
	public ModelAndView 탈퇴(Principal principal, HttpSession session) {
		// 로그아웃 후 삭제
		session.invalidate();
		service.delete(principal.getName());
		return new ModelAndView("redirect:/");
	}
}






