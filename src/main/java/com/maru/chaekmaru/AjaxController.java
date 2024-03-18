package com.maru.chaekmaru;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.member.MemberDto;
import com.maru.chaekmaru.member.MemberService;
import com.maru.chaekmaru.mypage.AttendenceDto;
import com.maru.chaekmaru.mypage.MyPointListDto;
import com.maru.chaekmaru.mypage.MypageService;
import com.maru.chaekmaru.review.ReviewDto;
import com.maru.chaekmaru.review.ReviewService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/ajax")
public class AjaxController {

	@Autowired
	MypageService mypageService;

	@Autowired
	ReviewService reviewService;

	@Autowired
	MemberService memberService;

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

	@PostMapping("/ismember")
	public boolean isMember(@RequestParam("m_id") String m_id) {

		log.info(m_id);

		int result = memberService.isMember(m_id);

		if (result != 1) {
			return true;
		} else {
			return false;
		}
	}

	@PostMapping("/member_modify_confirm")
	public String memberModifyConfirm(@RequestBody MemberDto memberDto, HttpSession session, Model model) {
		log.info("modify_confirm()");

		log.info(" ++++++++++++" + memberDto.getM_id());

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

	@PostMapping("/attendance")
	public String ajaxAttendance(HttpSession session, Model model, @RequestBody AttendenceDto attDto) {
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);
		int acc = attDto.getAc_attend_date() + 1;

		int result = -1;

		if (mypageService.checkTodayAttend(loginedMemberDto.getM_id()) == false) {
			result = mypageService.attendence(loginedMemberDto.getM_id());
		}

		if (result > 0) {
			MyPointListDto myPointListDto = new MyPointListDto();
			myPointListDto.setM_id(loginedMemberDto.getM_id());
			myPointListDto.setPl_payment_book_point(200);
			myPointListDto.setPl_desc(acc + "일째 출석 완료!");

			mypageService.chargePoint(myPointListDto);

			if (acc % 10 == 0) {
				MyPointListDto myPointListDto2 = new MyPointListDto();
				myPointListDto2.setM_id(loginedMemberDto.getM_id());
				myPointListDto2.setPl_payment_book_point(3000);
				myPointListDto2.setPl_desc(acc + "일 출석 보너스!");

				mypageService.chargePoint(myPointListDto2);
			}

			return "true";
		} else {
			return "false";
		}
	}

}
