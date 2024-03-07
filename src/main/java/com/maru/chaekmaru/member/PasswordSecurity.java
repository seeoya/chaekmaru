package com.maru.chaekmaru.member;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
public class PasswordSecurity {

	@Bean
	public PasswordEncoder passwordEncoder() {
		log.info("--passwordEncoder--");

		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable).formLogin(formLogin -> formLogin.loginPage("/member/temp"))
				.authorizeHttpRequests(authorizeRequest -> authorizeRequest
						.requestMatchers(AntPathRequestMatcher.antMatcher("/member/modify_form")).authenticated()
						.requestMatchers(AntPathRequestMatcher.antMatcher("**")).permitAll()
				);

		return http.build();

	}

}
