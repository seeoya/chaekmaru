package com.maru.chaekmaru.admin.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maru.chaekmaru.config.Config;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;


@Log4j2
@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {
	
	@Autowired
	AdminMemberService adminMemberService;
	
	@GetMapping("/create_account_form")
	public String createAccountForm() {
		log.info("createAccountForm()");
		
		String nextPage = "admin/member/create_account_form";
		
		return nextPage;		
		
	}
	
	@PostMapping("/create_account_confirm")
	public String createAccountConfirm(AdminMemberDto adminMemberDto, Model model) {
		log.info("createAccountConfirm()");
		
		String nextPage = "result";
				
		int result = adminMemberService.createAccountConfirm(adminMemberDto);
				
		switch (result) {
		case Config.INSERT_SUCCESS_AT_DATABASE: 
			model.addAttribute("result", Config.INSERT_SUCCESS_AT_DATABASE);
			break;

		case Config.ID_ALREADY_EXIST: 
			model.addAttribute("result", Config.ID_ALREADY_EXIST);
			break;
		
		case Config.INSERT_FAIL_AT_DATABASE: 
			model.addAttribute("result", Config.INSERT_FAIL_AT_DATABASE);
			break;

		}
		
		return nextPage;		
		
	}
	
	
	@GetMapping("/login_form")
	public String loginForm(){
		log.info("loginForm()");
		
		String nextPage = "admin/member/login_form";
		
		return nextPage;		
		
	}
	
	@PostMapping("/login_confirm")
	public String loginConfirm(AdminMemberDto adminMemberDto, Model model, HttpSession session) {
		log.info("loginConfirm()");
		
		String nextPage = "result";
				
		AdminMemberDto loginedAdminMemberDto = adminMemberService.loginConfirm(adminMemberDto);

		if(loginedAdminMemberDto != null) {
			session.setAttribute("loginedAdminMemberDto", loginedAdminMemberDto);
			session.setMaxInactiveInterval( 60 * 30 );		
			model.addAttribute("result", Config.LOGIN_SUCCESS);
		
		} else {
			model.addAttribute("result", Config.LOGIN_FAIL);			
			
		}		
		
		return nextPage;		
		
	}
	
	@GetMapping("/modify_account_form")
	public String modifyAccountForm() {
		log.info("modifyAccountForm()");
		
		String nextPage = "admin/member/modify_account_form";
		
		return nextPage;		
		
	}
	
	@PostMapping("/modify_account_confirm")
	public String modifyAccountConfirm(AdminMemberDto adminMemberDto, Model model, HttpSession session) {
		log.info("modifyAccountConfirm()");
		
		String nextPage = "result";
				
		AdminMemberDto  loginedAdminMemberDto = adminMemberService.modifyAccountConfirm(adminMemberDto);
				
		if(loginedAdminMemberDto != null) {
			
			session.setAttribute("loginedAdminMemberDto", loginedAdminMemberDto);
			session.setMaxInactiveInterval( 60 * 30 );		
			
			model.addAttribute("result", Config.MODIFY_SUCCESS_AT_DATABASE);
		
		} else 
 		
			model.addAttribute("result", Config.MODIFY_FAIL_AT_DATABASE);
		
		
		return nextPage;			
				
	}
	
	
	@GetMapping("/logout_confirm")
	public String logoutConfirm(HttpSession session) {
		log.info("logoutConfirm()");
		
		String nextPage = "redirect:/";
		
		session.removeAttribute("loginedAdminMemberDto");
		
		return nextPage;		
		
	}
	

	@GetMapping("/delete_account_confirm")
	public String deleteAccountConfirm(@RequestAttribute("a_no") int a_no, Model model) {
		log.info("deleteAccountConfirm()");
		
		String nextPage = "result";
		
		int result = adminMemberService.deleteAccountConfirm(a_no);
		
		switch (result) {
		case Config.DELETE_SUCCESS_AT_DATABASE: 
			model.addAttribute("result", Config.DELETE_SUCCESS_AT_DATABASE);
			break;
		
		case Config.DELETE_FAIL_AT_DATABASE: 
			model.addAttribute("result", Config.DELETE_FAIL_AT_DATABASE);
			break;

		}
		
		
		
		return nextPage;		
		
	}
	
	

	@GetMapping("/admin_list_form")
	public String adminListForm(Model model) {
		log.info("adminListForm()");
		
		String nextPage = "admin/member/admin_list_form";
		
		List<AdminMemberDto> adminMemberDtos = adminMemberService.adminListForm();
		
		model.addAttribute("adminMemberDtos", adminMemberDtos);
				
		return nextPage;		
		
	}
	
	
	
	@GetMapping("/set_admin_approval")
	public String setAdminApproval(@RequestParam("a_no") int a_no, Model model) {
		log.info("setAdminApproval()");
		
		String nextPage = "result";
				
		int result = adminMemberService.setAdminApproval(a_no);
				
		switch (result) {
		case Config.SET_ADMIN_APPROVAL_SUCCESS_AT_DATABASE: 
			model.addAttribute("result", Config.SET_ADMIN_APPROVAL_FAIL_AT_DATABASE);
			break;
		
		case Config.SET_ADMIN_APPROVAL_FAIL_AT_DATABASE: 
			model.addAttribute("result", Config.SET_ADMIN_APPROVAL_FAIL_AT_DATABASE);
			break;

		}
		
		return nextPage;		
		
	}
	
}
