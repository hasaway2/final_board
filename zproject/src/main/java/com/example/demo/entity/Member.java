package com.example.demo.entity;

import java.time.*;

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
}
