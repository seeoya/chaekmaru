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

		String nextPage = "member/create_account_form";

		return nextPage;

	}

	@PostMapping("/create_account_confirm")
	public String CreateAccountConfirm(MemberDto memberDto, Model model) {
		log.info("CreateAccountConfirm");

		String nextPage = "redirect:/member/login_form";

		int result = memberService.memberAccountConfirm(memberDto);
		// #TODO result 처리
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
	 * session.setAttribute("loginedMemberDto", loginedMemberDto);
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

		String nextPage = "member/modify_form";

		return nextPage;
	}

	@PostMapping("/modify_confirm")
	public String memberModifyConfirm(MemberDto memberDto, HttpSession session) {
		log.info("modify_confirm()");

		String nextPage = "redirect:/member/modify_form";

		MemberDto loginedMemberDto = memberService.modifyConfirm(memberDto);

		if (loginedMemberDto != null) {
			session.setAttribute("loginedMemberDto", loginedMemberDto);
			session.setMaxInactiveInterval(60 * 30);
		} else {
			// #TODO result 처리
			nextPage = "/result";
		}

		return nextPage;
	}

	@GetMapping("/logout_confirm")
	public String logoutConfirm(HttpSession session) {
		log.info("logoutConfirm()");

		session.removeAttribute("loginedMemberDto");

		// #TODO result 처리
		String nextPage = "redirect:/member/login_form";

		return nextPage;
	}

	@GetMapping("/delete_confirm")
	public String deleteConfirm(HttpSession session) {
		log.info("deleteConfirm()");

		String nextPage = "redirect:/member/logout_confirm";

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");

		int result = memberService.memberDeleteConfirm(loginedMemberDto.getM_id());
		if (result <= 0) {
			// #TODO result 처리
			nextPage = "/result";
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

		// #TODO result 처리
		if (id != null) {
			memberService.sendEmail(email, "Your ID is : " + id);
			return nextPage = "redirect:/member/login_form";
		} else {
			return nextPage = "redirect:/member/find_id_form";
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

		// #TODO result 처리
		if (getId != null) {
			memberService.sendEmail(email, message);
			return "redirect:/member/login_form";
		} else {
			return "redirect:/member/find_pw_form";
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
		int result = memberService.pwModifyConfirm(id, m_pw);

		// #TODO result 처리
		if (result > 0) {
			return "redirect:/member/logout_confirm";
		} else {
			return "redirect:/result";
		}
	}

}
