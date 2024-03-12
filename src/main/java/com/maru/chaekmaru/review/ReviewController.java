package com.maru.chaekmaru.review;

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
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	ReviewService reviewService;

	@PostMapping("/write_confirm")
	public String writeConfirm(Model model, HttpSession session, ReviewDto reviewDto) {
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		reviewDto.setM_id(loginedMemberDto.getM_id());

		int result = -1;
		result = reviewService.writeConfirm(reviewDto);

		model.addAttribute("result", result);
		model.addAttribute("b_no", reviewDto.getB_no());
		return "result";
	}

	@PostMapping("/modify_confirm")
	public String deleteReview(Model model, HttpSession session, ReviewDto reviewDto) {
		int result = -1;

		result = reviewService.modifyConfirm(reviewDto);

		model.addAttribute("result", result);
		return "result";
	}

	@GetMapping("/delete_confirm")
	public String deleteReview(Model model, @RequestParam("r_no") int r_no, @RequestParam("b_no") int b_no) {
//		nextPage = "redirect:/book/view/" + b_no;
		int result = -1;

		result = reviewService.deleteConfirm(r_no);

		model.addAttribute("result", result);
		return "result";
	}

}
