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
import com.maru.chaekmaru.member.MemberService;
import com.maru.chaekmaru.mypage.MypageService;
import com.maru.chaekmaru.review.ReviewDto;
import com.maru.chaekmaru.review.ReviewService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.web.bind.annotation.RequestBody;


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
	
	@PostMapping("/pw_mail_send")
	public String findPwConfirm(@RequestBody MemberDto memberDto, Model model) {
		log.info("findPwConfirm()");
		
		String id = memberDto.getM_id();
		String name = memberDto.getM_name();
		String email = memberDto.getM_mail();
		
		MemberDto getId = memberService.findMember(id, name, email);

		String link = "http://localhost:8090/member/pw_modify_form?id=" + id;
		String logoUrl = "http://localhost:8090/img/logo3.png";
		String message = "<div style='width: 400px; margin: 20px auto; padding: 30px 50px; border: 1px solid #eaeaea; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);'>" +
		        "<img src='" + logoUrl + "' alt='Logo' style='display: block; margin: 0 auto 20px; width: 100px;'>" + // 로고 이미지 크기 조절
		        "<h2 style='color: #333; text-align: center; margin: 0 0 20px;'>" + // 헤더 가운데 정렬
		        "비밀번호 변경 안내</h2>" +
		        "<p style='color: #555; text-align: center;'>" + // 본문 가운데 정렬
		        "아래의 링크를 클릭하여 비밀번호를 변경하세요.</p>" +
		        "<a href='" + link + "' style='display: block; margin-top: 20px; text-align: center; padding: 10px 20px; color: #fff; background-color: #365a41; border-radius: 5px; text-decoration: none;'>" + // 버튼 스타일 조정
		        "비밀번호 변경하기</a>" +
		        "</div>";

		if (getId != null) {
			memberService.sendEmail(email, message);
			model.addAttribute("result", Config.FIND_PW_SUCCESS);
		} else {
			model.addAttribute("result", Config.FIND_PW_FAIL);
		}
		
		return "result";
	}

}
