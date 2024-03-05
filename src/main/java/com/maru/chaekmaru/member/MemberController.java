package com.maru.chaekmaru.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@GetMapping("/create_accout_form")
	public String CreateAccountForm() {
		log.info("-- CreateAccountForm() --");

		String nextPage = "member/create_account_form";

		return nextPage;

	}

	@PostMapping("/create_account_confirm")
	public String CreateAccountConfirm(MemberDto memberDto, Model model) {
		log.info("CreateAccountConfirm");

		String nextPage = "member/login_form";

		int result = memberService.memberAccountConfirm(memberDto);

		if (result == Config.INSERT_FAIL_AT_DATABASE) {
			nextPage = "member/create_account_form";
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

		String nextPage = "redirect:/";

		MemberDto loginedMemberDto = memberService.loginConfirm(memberDto);

		if (loginedMemberDto != null) {

			session.setAttribute("loginedMemberDto", loginedMemberDto);
			session.setMaxInactiveInterval(60 * 30);

		} else {
			nextPage = "redirect:/";

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

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");

		int result = memberService.memberDeleteConfirm(loginedMemberDto.getM_id());
		if (result <= 0) {
			nextPage = "/success";
		}

		return nextPage;

	}

	@GetMapping("/find_id_form")
	public String findIdForm() {
		log.info("findIdForm()");

		String nextPage = "member/find_id_form";

		return nextPage;
	}

	@PostMapping("/find_id_confirm")
	public String findIdAndSendEmail(@RequestParam("m_name") String name, @RequestParam("m_mail") String email) {
		String id = memberService.findIdByNameAndEmail(name, email);
		String nextPage = "";

		if (id != null) {
			memberService.sendEmail(email, "Your ID is : " + id);
			return nextPage = "member/login_form";
		} else {
			return nextPage = "member/find_id_form";
		}
	}

	@GetMapping("/find_pw_form")
	public String findPwForm() {
		log.info("findPwForm()");

		String nextPage = "member/find_pw_form";

		return nextPage;
	}

	@PostMapping("/find_pw_confirm")
	public String thereIsId(@RequestParam("m_id") String id, @RequestParam("m_name") String name,
		@RequestParam("m_mail") String email) {
		log.info("thereIsId()");
		MemberDto getId = memberService.thereIsId(id, name, email);

		String link = "http://localhost:8090/member/pw_modify_form?id=" + id;
		String message = "비밀번호 변경 링크 : " + link;
		
		if (getId != null) {
			memberService.sendEmail(email, message);
			return "member/login_form";
		} else {
			return "member/find_pw_form";
		}
	}

	@GetMapping("/pw_modify_form")
	public String pwModifyForm(@RequestParam("id") String id, Model model) {
		log.info("pwModifyForm()");
		log.info("-----------" + id);
		model.addAttribute("id", id);
		return "member/pw_modify_form";
	}
	

	@PostMapping("/pw_modify_confirm")
	public String pwModifyForm(@RequestParam("id") String id, @RequestParam("m_pw") String m_pw) {
	    log.info("pwModifyForm()");
	    log.info("+++++++++++" + id);
	    int result = memberService.pwModifyConfirm(id,m_pw);
	    
	    if (result > 0) {
	    	return "member/login_form";
	    } else {
	    	return "member/find_pw_form";
	    }

	}
	 
}
