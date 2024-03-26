package com.example.demo.controller;

import java.io.*;
import java.nio.file.*;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.*;

import jakarta.validation.*;
import jakarta.validation.constraints.*;

@Validated
@RestController
public class MemberRestController {
	@Autowired
	public MemberService service;
	
	// 1. 아이디 사용여부 확인
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/id-check")
	public ResponseEntity<String> idCheck(@NotEmpty(message="아이디를 입력하세요") String username) {
		Boolean result = service.idCheck(username);
		if(result)
			return ResponseEntity.ok("사용가능한 아이디입니다");
		return ResponseEntity.status(HttpStatus.CONFLICT).body("사용가능한 아이디입니다");
	}
	
	// 10. 이미지 출력
	@GetMapping(path="/profile/{image}", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> viewImage(@PathVariable String image) {
		try {
			Path path = Paths.get("c:/upload/profile/" + image);
			byte[] imageBytes = Files.readAllBytes(path);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
		} catch (IOException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	// 3. 아이디 찾기
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/find-username")
	public ResponseEntity<String> findUsername(@NotEmpty(message="이메일을 입력하세요") String email) {
		String username = service.findUsername(email);
		if(username==null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("사용자를 찾을 수 없습니다");
		return ResponseEntity.ok(username);
	}

	// 4. 비밀번호 찾기
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/reset-password")
	public ResponseEntity<String> resetPassword(@NotEmpty(message="아이디를 입력하세요") String username) {
		Boolean result = service.resetPassword(username);
		if(result)
			return ResponseEntity.ok("임시비밀번호를 이메일로 보냈습니다");
		return ResponseEntity.status(HttpStatus.CONFLICT).body("사용자를 찾을 수 없습니다");
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
		String message = e.getConstraintViolations().stream().findFirst().get().getMessage();
		return ResponseEntity.status(409).body(message);
	}
}
