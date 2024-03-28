package com.example.demo.service;

import java.io.*;
import java.util.*;

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
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private PasswordEncoder encoder;
	// 자바 메일 발송 객체
	// 메일을 보내려면 SMTP 서버가 필요 -> gmail을 이용해서 메일을 보내자
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("c:/upload/profile/")
	private String PROFILE_FOLDER;
	@Value("default.png")
	private String DEFAULT_PROFLIE;
	@Value("http://localhost:8081/profile/")
	private String PROFILE_URL;

	public Boolean idCheck(String username) {
		return !memberDao.existsByUsername(username);
	}
	
	public Boolean join(MemberJoinDto dto) {
		MultipartFile mf = dto.getProfile();
		String profileName = DEFAULT_PROFLIE;
		
		// 사진을 업로드하지 않은 경우
		// <input type="file" name="profile">이 비어있는 상태로 업로드된다
		// 이경우 MultipartFile은 null이 아니라 비어있다 -> isEmpty()
		if(mf.isEmpty()==false) {
			String 확장자 = FilenameUtils.getExtension(mf.getOriginalFilename());
			String 저장이름 = UUID.randomUUID().toString() + "." + 확장자;
			
			// 사진을 저장하기위해 파일 객체를 생성
			File file = new File(PROFILE_FOLDER, 저장이름);
			
			// 예외처리
			// try ~ catch : 개발자가 처리
			// throws : 스프링이 처리 -> 작업이 실패 메시지 출력
			try {
				mf.transferTo(file);
				profileName = 저장이름;
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		String 암호화비밀번호 = encoder.encode(dto.getPassword());
		Member member = dto.toEntity(암호화비밀번호, 
				PROFILE_URL + profileName);
		return memberDao.save(member)==1L;
	}

	public String findId(String email) {
		return memberDao.findUsernameByEmail(email);
	}

	// 보내는사람, 받는 사람, 제목, 내용
	// 메일을 직접 보내는 것이 아니라 gmail을 이용해 간접적으로 보내기 때문에
	// from은 여러분의 이메일 아이디로 바뀐다
	// 역시 같은 이유로 이메일 발송에 실패해도 우리는 알 수가 없다
	private Boolean sendMail(String from, String to, String title, String text) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(title);
			// html 활성화하도록 설정 : <a href="/aaa">aaa</a>
			helper.setText(text, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public Boolean resetPassword(String username) {
		Member m = memberDao.findByUsername(username);
		if(m==null) {
			return false;
		}
		// 20자리의 영숫자 임시비밀번호 생성
		String 임시비밀번호 = RandomStringUtils.randomAlphanumeric(20);
		String 암호화비밀번호 = encoder.encode(임시비밀번호);
		memberDao.changePassword(암호화비밀번호, username);
		
		return sendMail("admin", m.getEmail(), "임시비밀번호", 
				"임시비민번호 : " + 임시비밀번호);
	}

	public MemberReadDto read(String loginId) {
		Member member =  memberDao.findByUsername(loginId);
		return member.toReadDto();
	}

	public Boolean checkPassword(String password, String loginId) {
		String 암호화비밀번호 = memberDao.findByUsername(loginId).getPassword();
		return encoder.matches(password, 암호화비밀번호);
	}

	public Boolean delete(String loginId) {
		return memberDao.delete(loginId)==1L;
	}
	
	public Boolean changeEmail(String email ,String loginId) {
		return memberDao.changeEmail(email, loginId)==1L;
	}
}








