package com.maru.chaekmaru.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maru.chaekmaru.config.Config;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;

	@GetMapping("/create_account_form")
	public String CreateAccountForm() {
		log.info("-- CreateAccountForm() --");

		return "member/create_account_form";
	}

	@PostMapping("/create_account_confirm")
	public String CreateAccountConfirm(MemberDto memberDto, Model model) {
		log.info("CreateAccountConfirm");

		int result = memberService.createAccountConfirm(memberDto);
        model.addAttribute("result", result);

		return "result";
	}

	@GetMapping("/login_form")
	public String LoginForm() {
		log.info("-- LoginForm() --");

		return "member/login_form";
	}

	/*
	 * @PostMapping("/login_confirm") public String Loginconfirm(MemberDto
	 * memberDto, HttpSession session) { log.info("-- Loginconfirm() --");
	 * 
	 * String nextPage = "redirect:/";
	 * 
	 * MemberDto loginedMemberDto = memberService.loginConfirm(memberDto);
	 * 
	 * if (loginedMemberDto != null) {
	 * 
	 * session.setAttribute(Config.LOGINED_MEMBER_INFO, loginedMemberDto);
	 * session.setMaxInactiveInterval(60 * 30);
	 * 
	 * } else { nextPage = "redirect:/";
	 * 
	 * }
	 * 
	 * return nextPage;
	 * 
	 * }
	 */

	@GetMapping("/modify_form")
	public String modifyForm() {
		log.info("modifyForm()");

		return "member/modify_form";
	}

	@PostMapping("/modify_confirm")
	public String memberModifyConfirm(MemberDto memberDto, HttpSession session, Model model) {
		log.info("modify_confirm()");

		MemberDto loginedMemberDto = memberService.modifyConfirm(memberDto);

		if (loginedMemberDto != null) {
			session.setAttribute(Config.LOGINED_MEMBER_INFO, loginedMemberDto);
			model.addAttribute("result", Config.MEMBER_MODIFY_SUCCESS);
			session.setMaxInactiveInterval(60 * 30);
		} else {
			model.addAttribute("result", Config.MEMBER_NOT_FOUND);	
		}

		return "result";
	}

//	@GetMapping("/logout_confirm")
//	public String logoutConfirm(HttpSession session, Model model) {
//		log.info("logoutConfirm()");
//
//		session.removeAttribute(Config.LOGINED_MEMBER_INFO);
//		
//		model.addAttribute("result", Config.LOGOUT_SUCCESS);	
//
//		return "result";
//	}

	@GetMapping("/delete_confirm")
	public String deleteConfirm(HttpSession session, Model model) {
		log.info("deleteConfirm()");

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = memberService.memberDeleteConfirm(loginedMemberDto.getM_id());

		model.addAttribute("result", result);
		return "result";
	}

	@GetMapping("/find_id_form")
	public String findIdForm() {
		log.info("findIdForm()");

		return "member/find_id_form";
	}

	@PostMapping("/find_id_confirm")
	public String findIdAndSendEmail(@RequestParam("m_name") String name, @RequestParam("m_mail") String email, Model model) {

		String id = memberService.findIdByNameAndEmail(name, email);

		if (id != null) {
			memberService.sendEmail(email, "Your ID is : " + id);
			model.addAttribute("result", Config.FIND_ID_SUCCESS);
			
		} else {
			model.addAttribute("result", Config.FIND_ID_FAIL);
		}
		
		return "result";
	}

	@GetMapping("/find_pw_form")
	public String findPwForm() {
		log.info("findPwForm()");

		return "member/find_pw_form";
	}

	@PostMapping("/find_pw_confirm")
	public String findPwConfirm(@RequestParam("m_id") String id, @RequestParam("m_name") String name,
			@RequestParam("m_mail") String email, Model model) {
		log.info("findPwConfirm()");
		
		MemberDto getId = memberService.findMember(id, name, email);

		String link = "http://localhost:8090/member/pw_modify_form?id=" + id;
		String message = "비밀번호 변경 링크 : " + link;

		if (getId != null) {
			memberService.sendEmail(email, message);
			model.addAttribute("result", Config.FIND_PW_SUCCESS);
		} else {
			model.addAttribute("result", Config.FIND_PW_FAIL);
		}
		
		return "result";
	}

	@GetMapping("/pw_modify_form")
	public String pwModifyForm(@RequestParam("id") String id, Model model) {
		log.info("pwModifyForm()");
		log.info("-----------" + id);
		
		model.addAttribute("id", id);
		
		return "member/pw_modify_form";
	}

	@PostMapping("/pw_modify_confirm")
	public String pwModifyForm(@RequestParam("id") String id, @RequestParam("m_pw") String m_pw, Model model) {
		log.info("pwModifyForm()");
		log.info("+++++++++++" + id);
		
		int result = memberService.pwModifyConfirm(id, m_pw);

		model.addAttribute("result", result);
		
		return "result";
		
	}

}
