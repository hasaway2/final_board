package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.security.crypto.password.*;

import com.example.demo.dao.*;
import com.example.demo.entity.*;

@SpringBootTest
public class SecurityTest {
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//@Test
	public void saveUser() {
		String encodedPassword = passwordEncoder.encode("1234");
		Member member = new Member("summer", encodedPassword,"summer@naver.com", LocalDate.now(), LocalDate.now(), "summer.jpg", Role.USER);
		assertEquals(1L, memberDao.save(member));
	}
	
}
