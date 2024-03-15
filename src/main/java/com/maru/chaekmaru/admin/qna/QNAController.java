package com.maru.chaekmaru.admin.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.member.MemberDto;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/qNA")
public class QNAController {

	@Autowired
	QNAService qNAService;

	@GetMapping("/get_sb_no")
	public String getSbNo(Model model, HttpSession session) {

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		//		nextPage = "redirect:/qNA" + q_no;
		int result = -1;
		
		result = qNAService.getSbNo(loginedMemberDto.getM_id());

		model.addAttribute("result", result);
		return "result";
	}
	
	
	
	@PostMapping("/q_write_confirm")
	public String writeConfirm(Model model, HttpSession session, QNADto qNADto) {
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		qNADto.setM_id(loginedMemberDto.getM_id());

		int result = -1;
		result = qNAService.qWriteConfirm(qNADto);

		model.addAttribute("result", result);
		model.addAttribute("q_no", qNADto.getQ_no());
		return "result";
	}
	
	@PostMapping("/a_write_confirm")
	public String aWriteConfirm(Model model, QNADto qNADto) {
	
		int result = -1;
		result = qNAService.aWriteConfirm(qNADto);

		model.addAttribute("result", result);
		model.addAttribute("q_no", qNADto.getQ_no());
		return "result";
	}

	@PostMapping("/modify_confirm")
	public String modifyQNA(Model model, HttpSession session, QNADto qNADto) {
		int result = -1;

		result = qNAService.modifyConfirm(qNADto);

		model.addAttribute("result", result);
		return "result";
	}

	@GetMapping("/delete_confirm")
	public String deleteQNA(Model model, @RequestParam("q_no") int q_no) {
//		nextPage = "redirect:/qNA" + q_no;
		int result = -1;

		result = qNAService.deleteConfirm(q_no);

		model.addAttribute("result", result);
		return "result";
	}

}
