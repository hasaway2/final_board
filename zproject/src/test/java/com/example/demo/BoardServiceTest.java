package com.example.demo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import com.example.demo.service.*;

@SpringBootTest
public class BoardServiceTest {
	@Autowired
	private BoardService service;
	
	@Test
	public void listTest() {
		System.out.println(service.list(1));
		System.out.println(service.list(6));
		System.out.println(service.list(11));
	}
}
