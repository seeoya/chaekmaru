package com.maru.chaekmaru.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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

	@Bean
	public JavaMailSender javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost("smtp.naver.com");
		javaMailSender.setUsername("gmlqjm4192@gmail.com");
		javaMailSender.setPassword("gjymyflppndvbzmb");

		javaMailSender.setPort(587);

		javaMailSender.setJavaMailProperties(getMailProperties());

		return javaMailSender;
	}

	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.debug", "true");
		properties.setProperty("mail.smtp.ssl.trust", "smtp.naver.com");
		properties.setProperty("mail.smtp.ssl.enable", "true");
		return properties;
	}

}
