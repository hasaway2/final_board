package com.example.demo;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.security.web.access.*;

import com.example.demo.security.*;

/*
 * 1. 스프링 시큐리티
 * - 인증(폼 로그인) : @PreAuthorize + isAnonymous(), isAuthenticated()
 * - 인가(권한) : @Secured + "ROLE_권한이름"
 * - CSRF : 서버가 보내준 뷰 페이지의 위변조를 잡아낸다
 *          GET 방식으로 요청할 때 csrf 토큰을 뷰와 함께 보내준다
 *          POST 방식으로 요청할 때 csrf 토큰을 함께 보내면 백엔드에서 검증
 *          
 * 2. 스프링 시큐리티 태그 라이브러리
 * - <sec:authorize access=""> 태그를 이용한 인증과 인가
 * 		<sec:authorize access="isAuthenticated()">
 * 		<sec:authorize access="hasRole('ADMIN')">
 * - <sec:authentication property="name" />로 사용자 아이디를 가져올 수 있다
 * 
 * 3. 부가 클래스들
 * - 403 오류 처리 : AccessDeniedHandler 인터페이스 구현
 * - 로그인 실패했을 때 처리할 작업이 있다면 : SimpleUrlAuthenticationFailureHandler 클래스 상속
 * - 로그인 성공했을 때 처리할 작업이 있다면 :  SimpleUrlAuthenticationSuccessHandler 클래스 상속
 */


@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig {
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	@Autowired
	private MyLoginFailureHandler myLoginFailureHandler;

	// 리턴 객체를 스프링빈으로 등록
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// csrf 끄기
		// http.csrf(csrf->csrf.disable());

		http.formLogin(s -> s.loginPage("/member/login").loginProcessingUrl("/member/login").defaultSuccessUrl("/")
				.failureHandler(myLoginFailureHandler));
		http.logout(logout -> logout.logoutUrl("/member/logout").logoutSuccessUrl("/"));

		// 403처리
		http.exceptionHandling(e -> e.accessDeniedHandler(accessDeniedHandler));
		return http.build();
	}
}