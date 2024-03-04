package com.maru.chaekmaru.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maru.chaekmaru.config.Config;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;


@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;

	@GetMapping("/create_accout_form")
	public String CreateAccountForm() {
		log.info("-- CreateAccountForm() --");

		String nextPage = "member/create_account_form";

		return nextPage;

	}

	@PostMapping("/create_account_confirm")
	public String CreateAccountConfirm(MemberDto memberDto, Model model) {
		log.info("CreateAccountConfirm");

		String nextPage = "/loginsuccess";

		int result = memberService.memberAccountConfirm(memberDto);

		if (result == Config.INSERT_FAIL_AT_DATABASE) {
			nextPage = "/loginfail";
		}

		return nextPage;
	}

	@GetMapping("/login_form")
	public String LoginForm() {
		log.info("-- LoginForm() --");

		String nextPage = "member/login_form";

		return nextPage;

	}

	@PostMapping("/login_confirm")
	public String Loginconfirm(MemberDto memberDto, HttpSession session) {
		log.info("-- Loginconfirm() --");

		String nextPage = "/loginsuccess";

		MemberDto loginedMemberDto = memberService.loginConfirm(memberDto);

		if (loginedMemberDto != null) {

			session.setAttribute("loginedMemberDto", loginedMemberDto);
			session.setMaxInactiveInterval(60 * 30);

		} else {
			nextPage = "/loginfail";

		}

		return nextPage;

	}

	@GetMapping("/modify_form")
	public String modifyForm() {
		log.info("modifyForm()");

		String nextPage = "member/modify_form";

		return nextPage;
	}

	@PostMapping("/modify_confirm")
	public String memberModifyConfirm(MemberDto memberDto, HttpSession session) {
		log.info("modify_confirm()");

		String nextPage = "/success";

		MemberDto loginedMemberDto = memberService.modifyConfirm(memberDto);

		if (loginedMemberDto != null) {

			session.setAttribute("loginedMemberDto", loginedMemberDto);
			session.setMaxInactiveInterval(60 * 30);

		} else {

			nextPage = "/result";

		}

		return nextPage;

	}
	
	@GetMapping("/logout_confirm")
	public String logoutConfirm(HttpSession session) {
		log.info("logoutConfirm()");
		
		session.removeAttribute("loginedMemberDto");
		
		String nextPage = "redirect:/";
		
		return nextPage;
	}
	
	@GetMapping("/delete_confirm")
	public String deleteConfirm(HttpSession session) {
		log.info("deleteConfirm()");
		
		String nextPage = "redirect:/member/logout_confirm";

		MemberDto loginedMemberDto =
				(MemberDto) session.getAttribute("loginedMemberDto");
		
		int result = memberService.memberDeleteConfirm(loginedMemberDto.getM_id());
		if (result <= 0) {
	         nextPage = "/success";
		} 
      
		return nextPage;
		
	}
	
	

}
