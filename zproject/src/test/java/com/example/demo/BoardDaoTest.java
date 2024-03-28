package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.*;
import java.util.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import com.example.demo.dao.*;
import com.example.demo.entity.*;

@SpringBootTest
public class BoardDaoTest {
	@Autowired
	private BoardDao boardDao;
	
	//@Test
	public void saveTest() {
		List<String> writers = Arrays.asList("spring","summer","fall","winter");
		for(int i=0; i<123; i++) {
			boardDao.save(new Board(null, i+"번째 글", "내용없어요", writers.get(i%4), LocalDateTime.now(), 0L, 0L, 0L));
		}
	}
	
	//@Test
	public void findAllTest() {
		assertEquals(10L, boardDao.findAll(11L, 20L).size());
	}
	
	//@Test
	public void countTest() {
		assertEquals(124L, boardDao.count());
	}
}
