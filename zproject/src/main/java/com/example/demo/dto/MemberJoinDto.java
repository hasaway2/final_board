package com.example.demo.dto;

import java.time.*;

import org.springframework.format.annotation.*;
import org.springframework.web.multipart.*;

import com.example.demo.entity.*;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
public class MemberJoinDto {
	@NotEmpty(message="아이디는 필수입력입니다")
	@Pattern(regexp="^[A-Za-z0-9]{6,10}$", message="아이디는 영숫자 6~10자입니다")
	private String username;
	
	@NotEmpty(message="비밀번호는 필수입력입니다")
	@Pattern(regexp="^[A-Za-z0-9]{8,10}$", message="비밀번호는 영숫자 8~10자입니다")
	private String password;
	
	@NotEmpty(message="이메일은 필수입력입니다")
	@Email(message="잘못된 이메일입니다")
	private String email;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	
	private MultipartFile profile;
	
	public Member toEntity(String encodedPassword, String profileName) {
		return new Member(username, encodedPassword, email, birthday, LocalDate.now(), profileName, Role.USER);
	}
}
