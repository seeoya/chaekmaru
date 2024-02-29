package com.maru.chaekmaru.admin.member;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminLoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		
		HttpSession session = request.getSession(false);
		if(session != null) {
			Object object = session.getAttribute("loginedAdminMemberDto");
			
			if(object != null) {
				return true;
				
			}
		}
		
		response.sendRedirect(request.getContextPath() + "/admin/member/login_form");
		return false;
	}
	
}
