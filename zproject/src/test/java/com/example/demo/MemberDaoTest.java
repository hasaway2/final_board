package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import com.example.demo.dao.*;
import com.example.demo.entity.*;

@SpringBootTest
public class MemberDaoTest {
	@Autowired
	private MemberDao memberDao;
	
	//@Test
	public void saveTest() {
		Member member = new Member("summer", "1234","summer@naver.com", LocalDate.now(), LocalDate.now(), "summer.jpg", Role.USER);
		assertEquals(1L, memberDao.save(member));
	}
	
	//@Test
	public void existsByUsernameTest() {
		assertEquals(true, memberDao.existsByUsername("summer"));
		assertEquals(false, memberDao.existsByUsername("winter"));
	}
	
	//@Test
	public void findUsernameByEmailTest() {
		assertEquals("summer", memberDao.findUsernameByEmail("summer@naver.com"));
		assertNull(memberDao.findUsernameByEmail("winter@naver.com"));
	}
	
	//@Test
	public void changePasswordTest() {
		assertEquals(1L, memberDao.changePassword("1111", "summer"));
		assertEquals(0L, memberDao.changePassword("1111", "winter"));
	}
	
	//@Test
	public void findByUsernameTest() {
		assertNotNull(memberDao.findByUsername("summer"));
		assertNull(memberDao.findByUsername("winter"));
	}
	
	//@Test
	public void changeEmailTest() {
		assertEquals(1L, memberDao.changeEmail("summer@daum.net", "summer"));
		assertEquals(0L, memberDao.changeEmail("winter@daum.net", "winter"));
	}
	
	//@Test
	public void deleteByUsernameTest() {
		assertEquals(1L, memberDao.delete("summer"));
		assertEquals(1L, memberDao.delete("spring"));
	}
	
}
