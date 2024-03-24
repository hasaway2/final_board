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

@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig {
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	@Autowired
	private LoginFailureHanlder loginFailureHanlder;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(csrf->csrf.disable())
			.formLogin(s->s.loginPage("/member/login").loginProcessingUrl("/member/login").defaultSuccessUrl("/").failureHandler(loginFailureHanlder))
			.logout(s->s.logoutUrl("/member/logout").logoutSuccessUrl("/"))
			.exceptionHandling(e->e.accessDeniedHandler(accessDeniedHandler))
			.build();
	}
}
