package com.example.demo.entity;

public enum Role {
	USER("일반회원"), SELLER("판매회원"), ADMIN("관리자");
	private String korean;
	
	Role(String string) {
		this.korean = string;
	}
}
