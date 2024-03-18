package com.maru.chaekmaru.qna;

import java.util.ArrayList;

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
@RequestMapping("/qna")
public class QNAController {

	@Autowired
	QNAService qNAService;

	@GetMapping("/qna")
	public String qNAForm(HttpSession session, Model model) {
		log.info("qNAForm()");
			
			String nextPage = "qna/qna";
		
			MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

			ArrayList<QNADto> myQnas =  qNAService.setMyQNAs(loginedMemberDto.getM_id());
					
			ArrayList<QNADto> sbnos = qNAService.getSbData(loginedMemberDto.getM_id());
		

			model.addAttribute("sbnos", sbnos);
			model.addAttribute("myQnas", myQnas);	
			
			
			return nextPage;
	}
	
		
	@PostMapping("/q_write_confirm")
	public String qWriteConfirm(QNADto qNADto, Model model) {
		log.info("qWriteConfirm()");
	
		String nextPage = "result";
		int result = -1;
		result = qNAService.qWriteConfirm(qNADto);

		model.addAttribute("result", result);
		
		return nextPage;
	}
	
	@GetMapping("/qna_list_form")
	public String qNAList(Model model) {
		log.info("qNAList()");
			
			String nextPage = "qna/qna_list_form";
		
			ArrayList<QNADto> qnas =  qNAService.setQNAs();
		
			model.addAttribute("qnas", qnas);
			
			return nextPage;
				
	}

	@GetMapping("/qna_answer")
	public String qNAAnswer(@RequestParam("q_no") int q_no, Model model) {
		log.info("qNAForm()");
			
			String nextPage = "qna/qna_answer";
		
			QNADto qna =  qNAService.setQNA(q_no);
		
			model.addAttribute("qna", qna);
			
			return nextPage;
				
	}

	
	@PostMapping("/a_write_confirm")
	public String aWriteConfirm(QNADto qNADto, Model model) {
		log.info("aWriteConfirm()");
		
		String nextPage = "redirect:/qna/qna_list_form";
	
		qNAService.aWriteConfirm(qNADto);
	
		return nextPage;
	}


	@GetMapping("/delete_confirm")
	public String deleteQNA(@RequestParam("q_no") int q_no, Model model) {
		log.info("deleteQNA()");
		
		String nextPage = "result";
		
		int result = -1;

		result = qNAService.deleteConfirm(q_no);

		model.addAttribute("result", result);
		
		return nextPage;
	}

}
