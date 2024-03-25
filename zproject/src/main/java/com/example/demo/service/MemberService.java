package com.example.demo.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.example.demo.dao.*;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	
	public Boolean idCheck(String username) {
		return memberDao.existsByUsername(username)? false: true;
	}
}
