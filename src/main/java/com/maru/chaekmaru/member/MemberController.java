package com.maru.chaekmaru.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maru.chaekmaru.config.Config;

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
	
	public String CreateAccountConfirm(MemberDto memberDto,Model model) {
		log.info("CreateAccountConfirm");
		
		String nextPage = "/result";
		
		int result = memberService.memberAccountConfirm(memberDto);
		
		if (result <= Config.LOGIN_FAIL) {
			nextPage = "/result";
		}
		
		return nextPage;
	}
	
	
}
