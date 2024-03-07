package com.maru.chaekmaru.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	@Autowired
	IMemberDaoForMybatis iMemberDaoForMybatis;
	
	@Bean PasswordEncoder passwordEncoder() {
		log.info("--passwordEncoder--");

		return new BCryptPasswordEncoder();
		
	}

	@Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("filterChain()");
		
		http
			.cors(cors -> cors.disable())
			.csrf(csrf -> csrf.disable());
						
		http
			.authorizeHttpRequests(request -> request
					.requestMatchers(
						"/member/modify_form"
						).authenticated()
					.requestMatchers(
						"/",
						"/css/**",
						"/img/**",
						"/js/**",
						"/member/**"
						).permitAll());
						
		http
			.formLogin((login) -> login
					.loginPage("/member/login_form")
					.loginProcessingUrl("/member/login_confirm")
					.usernameParameter("m_id")
					.passwordParameter("m_pw")
					.successHandler((request, response, authentication) -> {
									log.info("success handler");
									
						MemberDto memberDto = new MemberDto();
						memberDto.setM_id(authentication.getName());
						
						MemberDto loginedMemberDto = iMemberDaoForMybatis.selectMember(memberDto.getM_id());
						
						HttpSession session = request.getSession();
						session.setAttribute("loginedMemberDto", loginedMemberDto);
						session.setMaxInactiveInterval(60 * 30);
						
						response.sendRedirect("/");
									
					})
					.failureHandler((request, response, exception) -> {
						log.info("fail handler!!");
								log.error("Login failed: " + exception.getMessage());
								response.sendRedirect("/member/member_login_form");
									
					}));
							
		http
			.logout(logout -> logout
				.logoutUrl("/member/logout_confirm")
				.logoutSuccessHandler((request, response, authentication) -> {
					log.info("logoutSuccessHandler");
									
					HttpSession session = request.getSession();
					session.invalidate();
									
					response.sendRedirect("/");
									
					}));
									

								
		return http.build();
		
		}
}
