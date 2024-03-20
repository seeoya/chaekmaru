package com.maru.chaekmaru;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maru.chaekmaru.admin.book.AdminBookService;
import com.maru.chaekmaru.admin.member.AdminMemberDto;
import com.maru.chaekmaru.admin.member.AdminMemberService;
import com.maru.chaekmaru.admin.shop.AdminShopService;
import com.maru.chaekmaru.book.BookDto;
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
	
	@Autowired
	AdminBookService adminBookService;
	
	@Autowired
	AdminMemberService adminMemberService;
	
	@Autowired
	AdminShopService adminShopService;

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
	
	@PostMapping("/find_id_send")
	public String findIdAndSendEmail(@RequestBody MemberDto memberDto, Model model) {
		
		String name = memberDto.getM_name();
		String email = memberDto.getM_mail();
		String logoUrl = "http://localhost:8090/img/logo3.png";
		String id = memberService.findIdByNameAndEmail(name, email);
		String htmlContent = "<div style='width: 400px; margin: 20px auto; padding: 30px 50px;'>" +
                "<div style='text-align: center; background-color: #ffffff; border: 1px solid #eaeaea; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); padding: 20px; border-radius: 10px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);'>" +
                "<img src='" + logoUrl + "' alt='Logo' style='width: 150px; margin-bottom: 20px;'>" +
                "<h2 style='color: #333;'>아이디 찾기 결과</h2>" +
                "<p style='color: #555;'>요청하신 아이디는 아래와 같습니다.</p>" +
                "<div style='background-color: #f0f0f0; padding: 20px; margin-top: 20px; border-radius: 5px;'>" +
                "<strong style='color: #333; font-size: 24px;'>" + id + "</strong>" +
                "</div>" +
                "<p style='margin-top: 20px; color: #777;'>해당 아이디로 로그인 후 서비스를 이용해주세요.</p>" +
                "</div>" +
                "</div>";
		
		
		if (id != null) {
			memberService.sendIdEmail(email,htmlContent);
			String mailSuccess = "sendSuccess";
			model.addAttribute("result", Config.FIND_ID_SUCCESS);
			return mailSuccess;
		} else {
			String mailFail = "sendFail";
			model.addAttribute("result", Config.FIND_ID_FAIL);
			return mailFail;
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
			String mailSuccess = "sendSuccess";
			return mailSuccess;
		} else {
			model.addAttribute("result", Config.FIND_PW_FAIL);
			String mailFail = "sendFail";
			return mailFail;
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
	
	@PostMapping("/delete_member_pick")
	public String deleteMyPick(HttpSession session, Model model, @RequestParam("b_no") int b_no) {
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.deleteMyPick(loginedMemberDto.getM_id(), b_no);
		
		if (result > 0) {
			return "true";
		} else {
			return "false";
		}
	}
	
	@PostMapping("/delete_mycart_confirm")
	public String deleteMyCart(HttpSession session, Model model, @RequestParam("c_no") int c_no) {
		log.info("deleteMyCartList");

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.deleteMyCart(loginedMemberDto.getM_id(), c_no);
		
		if (result > 0) {
			return "true";
		} else {
			return "false";
		}
	}
	
	
	// 도서 정보 수정
	@PostMapping("/modify_book_confirm")
	public String modifyBookConfirm(@RequestBody BookDto bookDto, Model model) {
		log.info("modifyBookConfirm()");
								
		int result = adminBookService.modifyBookConfirm(bookDto);
				

		if (result > 0) {
			return "true";
		} else {
			return "false";
		}
				
	}
	
	// 도서 삭제
	@PostMapping("/delete_book_confirm")
	public String deleteBookConfirm(@RequestParam(value = "b_no") int b_no) {
		log.info("deleteBookConfirm()");		
		
		int result = adminBookService.deleteBookConfirm(b_no);
		
		if (result < 0) {
			return "false";
		} else {
			return "true";
		}
					
			
	}
	
	
	// 관리자 정보 수정
	@PostMapping("/modify_account_confirm")
	public String modifyAccountConfirm(@RequestBody AdminMemberDto adminMemberDto, HttpSession session) {
		log.info("modifyAccountConfirm()");
				
		AdminMemberDto  loginedAdminMemberDto = adminMemberService.modifyAccountConfirm(adminMemberDto);
				
		if(loginedAdminMemberDto != null) {
			
			session.setAttribute("loginedAdminMemberDto", loginedAdminMemberDto);
			session.setMaxInactiveInterval( 60 * 30 );		
			return "true";
		} else {
				return "false";
		}
	
				
	}
	
	// 관리자계정 삭제
	@PostMapping("/delete_account_confirm")
	public String deleteAccountConfirm(@RequestParam(value = "a_no") int a_no) {
		log.info("deleteAccountConfirm()");
		log.info(a_no);		
		
		int result = adminMemberService.deleteAccountConfirm(a_no);
		
		if (result < 0) {
			return "false";
		} else {
			return "true";
		}
		
			
	}
	
	// 회원계정 비활성화	
	@PostMapping("/user_account_active_confirm")
	public String userAccountActiveConfirm(@RequestParam(value = "m_id") String m_id) {
		log.info("userAccountActiveConfirm()");	
				
		int result = adminShopService.userAccountActiveConfirm(m_id);
		
		if (result < 0) {
			return "false";
		} else {
			return "true";
		}
	}
	
	// 로그인 세션 갱신	
	@PostMapping("/refresh_member_info")
	public MemberDto refreshMemberInfo(HttpSession session) {
		log.info("refreshMemberInfo()");	
		
		MemberDto loginedMemberInfo = memberService.refreshMemberInfo(session);
		
		return loginedMemberInfo;
	}
	

}
