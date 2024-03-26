package com.example.demo.service;

import java.io.*;

import org.apache.commons.io.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.javamail.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.*;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.*;

import jakarta.mail.*;
import jakarta.mail.internet.*;

@Service
public class MemberService {
	@Value("${profile.default}")
	private String DEFAULT_PROFILE_NAME;
	@Value("${profile.folder}")
	private String PROFILE_FOLDER;
	@Value("${profile.url}")
	private String PROFILE_URL;
	
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JavaMailSender mailSender;
	
	public Boolean idCheck(String username) {
		return memberDao.existsByUsername(username)? false: true;
	}
	
	// 프로필 이미지를 저장 + 비밀번호 암호화 후 member 저장 
	public Boolean join(MemberJoinDto dto) {
		MultipartFile mf = dto.getProfile();
		String profileName = DEFAULT_PROFILE_NAME;
		if(mf!=null && mf.isEmpty()==false) {
			String ext = FilenameUtils.getExtension(mf.getOriginalFilename());
			String saveProfileName = dto.getUsername() + "." + ext;
			File file = new File(PROFILE_FOLDER, saveProfileName);
			try {
				mf.transferTo(file);
				profileName = saveProfileName;
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		String encodedPassword = passwordEncoder.encode(dto.getPassword());
		Member member = dto.toEntity(encodedPassword, PROFILE_URL + profileName);
		return memberDao.save(member)==1L;
	}

	public String findUsername(String email) {
		return memberDao.findUsernameByEmail(email);
	}
	
	private Boolean sendMail(String from, String to, String title, String text) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText(text, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public Boolean resetPassword(String username) {
		Member member = memberDao.findByUsername(username);
		if(member==null)
			return false;
		String newPassword = RandomStringUtils.randomAlphanumeric(20);
		String newEncodedPassword = passwordEncoder.encode(newPassword);
		memberDao.changePassword(newEncodedPassword, username);
		return sendMail("admin@icia.com", member.getEmail(), "임시비밀번호", newPassword);
	}

	public Boolean checkPassword(String password, String loginId) {
		Member member = memberDao.findByUsername(loginId);
		if(member==null)
			return false;
		return passwordEncoder.matches(password, member.getPassword());
	}

	public MemberReadDto read(String loginId) {
		Member member = memberDao.findByUsername(loginId);
		return member==null? null:member.toDto();
	}

}
