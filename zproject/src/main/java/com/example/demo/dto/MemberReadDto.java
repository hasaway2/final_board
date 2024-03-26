package com.example.demo.dto;

import java.time.*;

import lombok.*;

@Getter
@AllArgsConstructor
public class MemberReadDto {
	private String username;
	private String email;
	private LocalDate birthday;
	private LocalDate joinday;
	private Long days;
	private String profile;	
	private String role;
}
