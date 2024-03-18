package com.maru.chaekmaru;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.member.MemberDto;
import com.maru.chaekmaru.member.MemberService;
import com.maru.chaekmaru.mypage.AttendenceDto;
import com.maru.chaekmaru.mypage.MemberCartDto;
import com.maru.chaekmaru.mypage.MyPointListDto;
import com.maru.chaekmaru.mypage.MypageService;
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

	@PostMapping("/cart_modify_form")
	public String cartModifyForm(@RequestBody MemberCartDto memberCartDto, HttpSession session, Model model) {
		log.info("cart_modify_form()");

		int c_no = memberCartDto.getC_no();
		int c_book_count = memberCartDto.getC_book_count();

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.addBookCount(loginedMemberDto.getM_id(), c_no, c_book_count);

		if (result > 0) {
			return "true";
		} else {
			return "false";
		}
	}

	@PostMapping("/add_my_cart")
	public String addMyCart(HttpSession session, Model model, @RequestBody MemberCartDto memberCartDto) {
		int b_no = memberCartDto.getB_no();
		int c_book_count = memberCartDto.getC_book_count();

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.addMyCart(loginedMemberDto.getM_id(), b_no, c_book_count);

		if (result > 0) {
			return "true";
		} else {
			return "false";
		}
	}

	@PostMapping("/add_member_pick")
	public String addMemberPick(@RequestParam("b_no") int b_no, HttpSession session, Model model) {
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.addMyPick(loginedMemberDto.getM_id(), b_no);

		if (result == Config.ADD_PICK_SUCCESS) {
			return "true";
		} else if(result == Config.ADD_PICK_DUPPLICATE) {
			return "duple";
		} else {
			return "false";
		}
	}

}
