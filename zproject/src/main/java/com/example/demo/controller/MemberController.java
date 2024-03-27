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
import jakarta.validation.constraints.*;

@Validated
@Controller
public class MemberController {
	@Autowired
	public MemberService service;
	
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
	public void find() {
	}
	
	@Secured("ROLE_USER")
	@GetMapping("/member/check-password")
	public void checkPassword() {
	}
	
	// 2. 회원 가입
	@PreAuthorize("isAnonymous()")
	@PostMapping("/member/join")
	public ModelAndView join(@ModelAttribute @Valid MemberJoinDto dto, BindingResult bindingResult) {
		service.join(dto);
		return new ModelAndView("redirect:/member/login");
	}
	
	// 5. 비밀번호 확인
	@Secured("ROLE_USER")
	@PostMapping("/member/check-password")
	public ModelAndView checkPassword(@NotEmpty(message="비밀번호는 필수입력입니다") String password, Principal principal, HttpSession session, RedirectAttributes ra) {
		Boolean result = service.checkPassword(password, principal.getName());
		
		// 비밀번호가 틀리면 비밀번호 확인으로 재이동(ra에 오류 메시지 저장)
		// 비밀번호를 확인한 경우 세션에 확인 사실을 저장 후 내정보 보기로
		if (result == false) {
			ra.addFlashAttribute("msg", "비밀번호를 확인하지 못했습니다");
			return new ModelAndView("redirect:/member/check-password");
		} else {
			session.setAttribute("checkPassword", true);
			return new ModelAndView("redirect:/member/read");
		}
	}

	// 6. 내정보 보기
	@Secured("ROLE_USER")
	@GetMapping("/member/read")
	public ModelAndView read(Principal principal, HttpSession session) {
		// 비밀번호를 확인하지 않았다면 비밀번호 확인으로
		if (session.getAttribute("checkPassword") == null)
			return new ModelAndView("redirect:/member/check-password");
		MemberReadDto dto = service.read(principal.getName());
		return new ModelAndView("member/read").addObject("member", dto);
	}
	
	// 8. 회원 탈퇴 : 탈퇴와 로그아웃 함께 진행
	@Secured("ROLE_USER")
	@PostMapping("/member/quit")
	public ModelAndView quit(Principal principal, HttpSession session) {
		session.invalidate();
		service.quit(principal.getName());
		return new ModelAndView("redirect:/");
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ModelAndView constraintViolationExceptionHandler(ConstraintViolationException e, RedirectAttributes ra) {
		String msg = e.getConstraintViolations().stream().findFirst().get().getMessage();
		ra.addFlashAttribute("msg", msg);
		return new ModelAndView("redirect:/member/join");
	}
}
