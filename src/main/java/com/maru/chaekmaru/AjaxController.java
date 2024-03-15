package com.maru.chaekmaru;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.member.MemberDto;
import com.maru.chaekmaru.mypage.MypageService;
import com.maru.chaekmaru.review.ReviewDto;
import com.maru.chaekmaru.review.ReviewService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import oracle.jdbc.proxy.annotation.Post;

@Log4j2
@RestController
@RequestMapping("/ajax")
public class AjaxController {

	@Autowired
	MypageService mypageService;

	@Autowired
	ReviewService reviewService;

	@GetMapping("/test")
	public String ajaxTest() {
		log.info("11111");

		return "ajax 통신 완료";
	}

	@PostMapping("/test2")
	public String ajaxTest2(@RequestParam("b_no") int b_no) {
		log.info("2222");

		return "ajax 통신 완료" + b_no;
	}

	@PostMapping("/addCart")
	public String ajaxAddCart(HttpSession session, Model model, @RequestParam("b_no") int b_no) {
		log.info("addCart");
		String text = "장바구니 추가 실패";

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.addMyCart(loginedMemberDto.getM_id(), b_no);

		if (result > 0) {
			text = "장바구니 추가 성공";
		}

		return text;
	}

	@PostMapping("/deleteCart")
	public String ajaxDeleteCart(HttpSession session, Model model, @RequestParam("c_no") int c_no) {
		log.info("addCart");
		String text = "장바구니 제거 실패";

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.deleteMyCart(loginedMemberDto.getM_id(), c_no);

		if (result > 0) {
			text = "장바구니 제거 성공";
		}

		return text;
	}

	@PostMapping("/write_confirm")
	public String ajaxWriteConfirm(Model model, HttpSession session, ReviewDto reviewDto) {
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		reviewDto.setM_id(loginedMemberDto.getM_id());

		int result = -1;
		result = reviewService.writeConfirm(reviewDto);

		if (result > 0) {
			return "리뷰 등록 완료";
		} else {
			return "리뷰 등록 실패";
		}

	}

	@PostMapping("/attendance")
	public String ajaxAttendance(HttpSession session, Model model) {
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = -1;

		result = mypageService.attendence(loginedMemberDto.getM_id());

		if (result > 0) {
			return "true";
		} else {
			return "false";
		}
	}

}
