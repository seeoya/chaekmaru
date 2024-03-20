package com.maru.chaekmaru.mypage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.member.MemberDto;
import com.maru.chaekmaru.member.MemberService;
import com.maru.chaekmaru.review.ReviewDto;
import com.maru.chaekmaru.review.ReviewService;
import com.maru.chaekmaru.shop.SaledBookDto;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	MypageService mypageService;

	@Autowired
	ReviewService reviewService;

	@Autowired
	MemberService memberService;

	/*
	 * 장바구니 목록 페이지 이동
	 */
	@GetMapping("/member_cart_form")
	public String myCartList(Model model, HttpSession session) {
		log.info("myCartList");

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		List<MemberCartDto> memberCartDtos = mypageService.getMyCartList(loginedMemberDto.getM_id());

		model.addAttribute("memberCartDtos", memberCartDtos);

		return "mypage/member_cart_form";
	}

	/*
	 * 장바구니에서 수량 변경 클릭
	 */
	@GetMapping("/cart_modify_form")
	public String cartmodifyform(HttpSession session, Model model, @RequestParam("c_no") int c_no,
			@RequestParam("c_book_count") int c_book_count) {
		log.info("cartmodifyform");

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.addBookCount(loginedMemberDto.getM_id(), c_no, c_book_count);

		model.addAttribute("result", result);
		return "result";
	}

	/*
	 * 장바구니 페이지 장바구니 삭제 클릭
	 */
	@GetMapping("/delete_mycart_confirm")
	public String deleteMyCart(HttpSession session, Model model, @RequestParam("c_no") int c_no) {
		log.info("deleteMyCartList");

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.deleteMyCart(loginedMemberDto.getM_id(), c_no);

		model.addAttribute("result", result);
		return "result";
	}

	/*
	 * 결제 폼으로 이동
	 */
	@PostMapping("/payment_form")
	public String paymentMyCartForm(Model model, HttpSession session, @RequestParam("b_no") ArrayList<Integer> b_nos,
			@RequestParam("c_book_count") ArrayList<Integer> c_book_counts) {
		log.info("paymentMyCartListForm");

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		ArrayList<MemberCartDto> buyBooks = new ArrayList<>();

		for (int i = 0; i < b_nos.size(); i++) {
			buyBooks.add(new MemberCartDto(b_nos.get(i), c_book_counts.get(i)));
		}

		ArrayList<MemberCartDto> buyBooksDatas = mypageService.setPaymentForm(buyBooks);

		int allPrice = mypageService.calcAllPrice(buyBooksDatas);
		int discount = mypageService.memberDiscount(allPrice, loginedMemberDto.getM_grade());
		int currentPoint = mypageService.currentPoint(session);

		model.addAttribute("buyBooksDatas", buyBooksDatas);
		model.addAttribute("memberInfo", loginedMemberDto);

		model.addAttribute("allPrice", allPrice);
		model.addAttribute("discount", discount);
		model.addAttribute("finalPrice", allPrice - discount + 3000);
		model.addAttribute("currentPoint", currentPoint);

		return "mypage/all_payment_form";
	}

	/*
	 * 리뷰 페이지로 이동
	 */
	@GetMapping("/my_review")
	public String myReview(Model model, HttpSession session) {
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		ArrayList<ReviewDto> myReviews = reviewService.setMyReview(loginedMemberDto.getM_id());

		model.addAttribute("reviews", myReviews);

		return "/mypage/my_review";
	}

	/*
	 * 도서 상세 페이지에서 장바구니 버튼 클릭
	 */
	@GetMapping("/add_cart")
	public String moveMyCart(HttpSession session, Model model, @RequestParam("b_no") int b_no) {

		return "result";
	}

	/*
	 * 모두 결제 폼에서 결제하기 버튼 클릭
	 */
	@PostMapping("/all_payment_form_confirm")
	public String allPaymentMyCartList(HttpSession session, Model model, @ModelAttribute SaledBookDto saledBookDto,
			@RequestParam("b_no") ArrayList<Integer> b_nos,
			@RequestParam("c_book_count") ArrayList<Integer> c_book_counts) {
		log.info("<=====================allPaymentMyCartList==================>");
		// 회원 m_state == 2 결제 못함

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);
		int state = mypageService.getState(loginedMemberDto.getM_id());
		int result = -1;
		
		if (state != 2) {
			ArrayList<MemberCartDto> buyBooks = new ArrayList<>();

			for (int i = 0; i < b_nos.size(); i++) {
				buyBooks.add(new MemberCartDto(b_nos.get(i), c_book_counts.get(i)));
			}

			ArrayList<MemberCartDto> buyBooksDatas = mypageService.setPaymentForm(buyBooks);

			int allPrice = mypageService.calcAllPrice(buyBooksDatas);
			int discount = mypageService.memberDiscount(allPrice, loginedMemberDto.getM_grade());
			int finalPrice = allPrice - discount + 3000;

			result = mypageService.allPaymentMyCartList(loginedMemberDto.getM_id(), saledBookDto, buyBooksDatas,
					loginedMemberDto.getM_grade(), allPrice, discount, finalPrice);

			memberService.refreshPoint(session);
			
		} else {
			result = Config.MEMBER_BAN;
		}
		
		model.addAttribute("result", result);

		return "result";
	}

	/*
	 * 도서 구매 내역 페이지 이동
	 */
	@GetMapping("/payment_list_form")
	public String getPaymentList(HttpSession session, Model model) {
		log.info("getPaymentList");

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		LinkedHashMap<Integer, ArrayList<SaledBookDto>> list = mypageService
				.getMyPaymentList(loginedMemberDto.getM_id());
		LinkedHashMap<Integer, Integer> stateList = mypageService.getMyPaymentStateList(list);
		LinkedHashMap<Integer, Integer> allPriceByKey = mypageService.getMyAllPriceList(list);
		LinkedHashMap<Integer, Integer> saledPriceByKey = mypageService.saledPriceByKey(list);

		List<Integer> keySet = new ArrayList<>(list.keySet());
		List<Integer> stateKeySet = new ArrayList<>(stateList.keySet());
		List<Integer> allPriceKeySet = new ArrayList<>(allPriceByKey.keySet());
		List<Integer> saledPriceKeySet = new ArrayList<>(saledPriceByKey.keySet());

		// 키 값으로 내림차순 정렬
		Collections.reverse(keySet);
		Collections.reverse(stateKeySet);
		Collections.reverse(allPriceKeySet);
		Collections.reverse(saledPriceKeySet);

		model.addAttribute("list", list);
		model.addAttribute("stateList", stateList);
		model.addAttribute("allPriceByKey", allPriceByKey);
		model.addAttribute("saledPriceByKey", saledPriceByKey);

		return "mypage/payment_list_form";
	}

	/*
	 * 주문 취소
	 */
	@GetMapping("/cancel_payment_confirm")
	public String cancelPayment(HttpSession session, Model model, @RequestParam("sb_order_no") int sb_order_no) {
		// #TODO RESULT 페이지로 이동
//		String nextPage = "redirect:/mypage/payment_list_form";

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.cancelMyPaymentList(loginedMemberDto.getM_id(), sb_order_no);

		// #TODO result 세팅 필요
		if (result <= 0) {
            // 실패
			result = Config.CANCEL_PAYMENT_FAIL;
		} else {
			result = Config.CANCEL_PAYMENT_SUCCESS;
		}
		memberService.refreshPoint(session);
		model.addAttribute("result", result);
		return "result";
	}

	/*
	 * 반품 요청
	 */
	@GetMapping("/return_payment_confirm")
	public String returnPaymentList(HttpSession session, Model model, @RequestParam("sb_no") int sb_no,
			@RequestParam("b_no") int b_no) {
		log.info("returnPaymentList");

		// #TODO RESULT 처리 필요
//		String nextPage = "redirect:/mypage/payment_list_form";

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.returnRequestBookPayment(loginedMemberDto.getM_id(), sb_no, b_no);

		// #TODO result 세팅 필요
		if (result <= 0) {
			result = Config.RETURN_PAYMENT_FAIL;
		} else {
			result = Config.RETURN_PAYMENT_SUCCESS;
		}
		
		model.addAttribute("result", result);
		// #TODO RESULT 처리 필요
		return "result";
	}

	/*
	 * 찜 목록 페이지 이동
	 */
	@GetMapping("/member_pick")
	public String myPickList(Model model, HttpSession session) {
		log.info("myPickList");

		String nextPage = "mypage/member_pick_form";

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		List<MemberPickDto> memberPickDtos = mypageService.myPickList(loginedMemberDto.getM_id());

		model.addAttribute("memberPickDtos", memberPickDtos);

		return nextPage;
	}

	/*
	 * 찜 추가
	 */
	@GetMapping("/add_member_pick")
	public String addMyPick(HttpSession session, Model model, @RequestParam("b_no") int b_no) {
		log.info("addMyPick");

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.addMyPick(loginedMemberDto.getM_id(), b_no);

		model.addAttribute("result", result);
		return "result";

	}

	/*
	 * 찜 제거
	 */
	@GetMapping("/delete_member_pick")
	public String deleteMyPick(HttpSession session, Model model, @RequestParam("b_no") int b_no) {
		log.info("deleteMyCartList");

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);
		// #TODO RESULT 처리 필요
		int result = mypageService.deleteMyPick(loginedMemberDto.getM_id(), b_no);
		// #TODO RESULT 처리 필요
		model.addAttribute("result", result);
		return "result";
	}

	@GetMapping("/point_charge")
	public String pointCharge(HttpSession session, Model model) {

		int point = memberService.refreshPoint(session);
		model.addAttribute("point", point);

		return "/mypage/point_charge";
	}

	@GetMapping("/point_list")
	public String pointList(HttpSession session, Model model) {
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		ArrayList<MyPointListDto> myPointListDtos = mypageService.getPointList(loginedMemberDto.getM_id());
		int point = memberService.refreshPoint(session);

		model.addAttribute("items", myPointListDtos);
		model.addAttribute("point", point);

		return "/mypage/point_list";
	}

	@PostMapping("/point_charge_confirm")
	public String point_charge_confirm(HttpSession session, Model model, MyPointListDto myPointListDto) {
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		myPointListDto.setM_id(loginedMemberDto.getM_id());
		myPointListDto.setPl_desc("BP 충전");

		int result = mypageService.chargePoint(myPointListDto);
		memberService.refreshPoint(session);

		model.addAttribute("result", result);
		return "result";
	}

	@GetMapping("/attendance_list")
	public String attendanceList(HttpSession session, Model model) {
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		ArrayList<AttendenceDto> attendenceDtos = mypageService.getAttendenceList(loginedMemberDto.getM_id());
		int acc = mypageService.attendenceAcc(loginedMemberDto.getM_id());

		boolean todayAttend = mypageService.checkTodayAttend(loginedMemberDto.getM_id());

		log.info("acc" + acc);
		log.info("todayatt" + todayAttend);

		model.addAttribute("items", attendenceDtos);
		model.addAttribute("acc", acc);
		model.addAttribute("todayAttend", todayAttend);

		return "/mypage/attendance_list";
	}

	@GetMapping("/attendance")
	public String attendance(HttpSession session, Model model) {
		// ajax로 패치함
//		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);
//
//		int result = -1;
//
//		result = mypageService.attendence(loginedMemberDto.getM_id());
//		
		return "redirect:/mypage/attendance_list";
	}

	/*
	 * 주문 완료
	 */
	@GetMapping("/payment_confirm")
	public String confirmPayment(HttpSession session, Model model, @RequestParam("sb_order_no") int sb_order_no) {
		// #TODO RESULT 페이지로 이동
//		String nextPage = "redirect:/mypage/payment_list_form";

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.confirmPayment(loginedMemberDto.getM_id(), sb_order_no);

		// #TODO result 세팅 필요
		if (result <= 0) {
            result = Config.PAYMENT_CONFIRM_FAIL;
		} else {
			result = Config.PAYMENT_CONFIRM_SUCCESS;
		}

		model.addAttribute("result", result);
		return "result";
	}
}