package com.maru.chaekmaru.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.maru.chaekmaru.admin.member.AdminLoginInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new AdminLoginInterceptor()).excludePathPatterns("/css/**", "/img/**", "/js/**")
				.addPathPatterns("/admin/member/modify_form", "/admin/member/modify_confirm",
						"/admin/member/logout_confirm", "/admin/member/delete_confirm", "/admin/book/**",
						"/admin/shop/**");

	}

	

}
