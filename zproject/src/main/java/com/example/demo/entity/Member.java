package com.example.demo.entity;

import java.time.*;
import java.time.temporal.*;

import com.example.demo.dto.*;

import lombok.*;

@Data
@AllArgsConstructor
public class Member {
	private String username;
	private String password;
	private String email;
	private LocalDate birthday;
	private LocalDate joinday = LocalDate.now();
	private String profile;
	private Role role = Role.USER;
	
	public MemberReadDto toReadDto() {
		// ChronoUnit은 날짜 계산하는 클래스
		Long days = ChronoUnit.DAYS.between(joinday, LocalDate.now());
		return new MemberReadDto(email, birthday, joinday, days, profile);
	}
}







