package com.maru.chaekmaru.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.ui.Model;

import com.maru.chaekmaru.config.Config;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	IMemberDaoForMybatis iMemberDaoForMybatis;

	@Bean
	PasswordEncoder passwordEncoder() {
		log.info("--passwordEncoder--");

		return new BCryptPasswordEncoder();

	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("filterChain()");

		http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable());

		http.authorizeHttpRequests(request -> request.requestMatchers(
				"/ajax/member_modify_confirm",
				"/ajax/attendance",
				"/ajax/add_my_cart",
				"/ajax/add_member_pick",
				"/ajax/cart_modify_form",
				"/ajax/delete_member_pick",
				"/ajax/delete_mycart_confirm",
				"/member/modify_form",
				"/member/modify_confirm",
				"/member/delete_confirm",
				"/mypage/member_cart_form",
				"/mypage/cart_modify_form",
				"/mypage/delete_mycart_confirm",
				"/mypage/payment_form",
				"/mypage/payment_form_confirm",
				"/mypage/my_review",
				"/mypage/add_cart",
				"/mypage/all_payment_form_confirm",
				"/mypage/payment_list_form",
				"/mypage/cancel_payment_confirm",
				"/mypage/return_payment_confirm",
				"/mypage/member_pick",
				"/mypage/add_member_pick",
				"/mypage/delete_member_pick",
				"/mypage/point_charge",
				"/mypage/point_list",
				"/mypage/point_charge_confirm",
				"/mypage/attendance_list",
				"/qna/qna",
				"/qna/q_write_confirm",
				"/qna/delete_confirm",
				"/review/write_confirm",
				"/review/modify_confirm",
				"/review/delete_confirm"
				).authenticated()
				.requestMatchers("/**").permitAll());
		
		int result = 999;

		http.formLogin((login) -> login.loginPage("/result?result=" + result).loginProcessingUrl("/member/login_confirm")
				.usernameParameter("m_id").passwordParameter("m_pw")

				.successHandler((request, response, authentication) -> {
					log.info("success handler");

					MemberDto memberDto = new MemberDto();
					memberDto.setM_id(authentication.getName());

					MemberDto loginedMemberDto = iMemberDaoForMybatis.selectMember(memberDto.getM_id());

					HttpSession session = request.getSession();
					session.setAttribute(Config.LOGINED_MEMBER_INFO, loginedMemberDto);
					session.setMaxInactiveInterval(60 * 30);

					RequestCache requestCache = new HttpSessionRequestCache();
					SavedRequest savedRequest = requestCache.getRequest(request, response);

					String uri = "/result?result=" + Config.LOGIN_SUCCESS;

					/*
					 * if (savedRequest != null) { uri = savedRequest.getRedirectUrl();
					 * 
					 * requestCache.removeRequest(request, response); }
					 */
					
					response.sendRedirect(uri);
				}).failureHandler((request, response, exception) -> {
					log.info("fail handler!!");
					log.error("Login failed: " + exception.getMessage());
					
					String uri = "/result?result=" + Config.LOGIN_FAIL;
					
					response.sendRedirect(uri);
				}));

		http.logout(logout -> logout.logoutUrl("/member/logout_confirm")
				.logoutSuccessHandler((request, response, authentication) -> {
					log.info("logoutSuccessHandler");

					HttpSession session = request.getSession();
					session.removeAttribute(Config.LOGINED_MEMBER_INFO);

					String uri = "/result?result=" + Config.LOGOUT_SUCCESS;
					
					response.sendRedirect(uri);
				}));

		return http.build();

	}
}
