package com.example.demo.entity;

import java.time.*;
import java.time.temporal.*;

import com.example.demo.dto.*;

import lombok.*;

@Getter
@AllArgsConstructor
public class Member {
	private String username;
	private String password;
	private String email;
	private LocalDate birthday;
	private LocalDate joinday = LocalDate.now();
	private String profile;
	private Role role = Role.USER;
	
	public MemberReadDto toDto() {
		Long days =	ChronoUnit.DAYS.between(joinday, LocalDate.now());
		return new MemberReadDto(username, email, birthday, joinday, days, profile, role.name());
	}
}
