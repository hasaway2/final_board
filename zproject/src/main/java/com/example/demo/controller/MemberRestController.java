package com.example.demo.controller;

import java.io.*;
import java.nio.file.*;
import java.nio.file.Path;
import java.security.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.*;

import jakarta.validation.*;
import jakarta.validation.constraints.*;


// rest 전용 컨트롤러
@RestController
public class MemberRestController {
	@Autowired
	private MemberService service;
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> CVEHandler(ConstraintViolationException e) {
		String msg = e.getConstraintViolations().stream()
			.findFirst().get().getMessage();
		return ResponseEntity.status(409).body(msg);
	}
	
	@GetMapping("/member/id-check")
	public ResponseEntity<?> idCheck(@NotEmpty(message="아이디는 필수입력입니다") String username) {
		Boolean result = service.idCheck(username);
		if(result==true) {
			return ResponseEntity.ok("사용가능합니다");
		}
		return ResponseEntity.status(409).body("사용중입니다");
	}
	
	// 1. byte[]은 뭐야? 파일을 출력할 때 byte의 배열로 지정한다
	// 2. @GetMapping()이 왜 복잡하냐?
	// - 어노테이션은 변형된 클래스
	// - @~Mapping 어노테이션에는 path라는 필드가 기본 필드
	// - produces 필드는 ResponseEntity에 담긴 데이터 형식을 웹 브라우저에 알려준다
	@GetMapping(path="/profile/{image}", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> viewImage
		(@PathVariable String image) {
		try {
			Path path = Paths.get("c:/upload/profile/" + image);
			byte[] imageBytes = Files.readAllBytes(path);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
		} catch (IOException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	// 3. 아이디 찾기
	@GetMapping("/member/find-id")
	public ResponseEntity<String> findId(String email) {
		String username = service.findId(email);
		if(username==null) {
			return ResponseEntity.status(409)
				.body("사용자를 찾을 수 없습니다");
		}
		return ResponseEntity.ok(username);
	}
	
	// 4. 비밀번호 리셋 - 비밀번호 찾기 기능은 개인정보보호법 위반
	@GetMapping("/member/reset-password")
	public ResponseEntity<String> resetPassword(String username) {
		Boolean result =  service.resetPassword(username);
		if(result==false) {
			return ResponseEntity.status(409).body("사용자를 찾을 수 없습니다");
		}
		return ResponseEntity.ok("임시비밀번호를 이메일로 보냈습니다");
	}
	
	// Response<여기>에서 여기에는 객체만 올 수 있다
	// 여기에 아무 객체도 안 담으려면 void형 객체 표현 Void를 사용한다
	@Secured("ROLE_USER")
	@PostMapping("/member/change-email")
	public ResponseEntity<Void> changeEmail(String email, 
		Principal principal) {
		Boolean result = service.changeEmail(email, principal.getName());
		if(result) {
			return ResponseEntity.ok(null);
		}
		return ResponseEntity.status(409).body(null);
	}
}










