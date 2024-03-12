package com.maru.chaekmaru.mypage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maru.chaekmaru.book.BookDto;
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
	 * 도서 상세 페이지 장바구니 클릭
	 */
	@PostMapping("/member_cart_form")
	public String addMyCart(HttpSession session, Model model, @RequestParam("b_no") int b_no) {
		log.info("addMyCart");

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.addMyCart(loginedMemberDto.getM_id(), b_no);

		model.addAttribute("result", result);
		return "result";
	}

	/*
	 * 마이 페이지 장바구니 삭제 클릭
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
	@GetMapping("/payment_form")
	public String paymentMyCartForm(Model model, HttpSession session, @RequestParam("c_no") int c_no) {
		log.info("paymentMyCartListForm");

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		List<MemberCartDto> memberCartDtos = mypageService.paymentForm(loginedMemberDto.getM_id(), c_no);

		int allPrice = mypageService.calcAllPrice(memberCartDtos);
		int discount = mypageService.memberDiscount(allPrice, loginedMemberDto.getM_grade());
        int currentPoint = mypageService.currentPoint(session);

		model.addAttribute("memberCartDtos", memberCartDtos);
		model.addAttribute("allPrice", allPrice);
		model.addAttribute("discount", discount);
		model.addAttribute("finalPrice", allPrice - discount + 3000);
        model.addAttribute("currentPoint", currentPoint);

		return "mypage/payment_form";
	}

	/*
	 * 결제 폼에서 결제하기 버튼 클릭
	 */
	@PostMapping("/payment_form_confirm")
	public String paymentMyCartList(HttpSession session, Model model, @ModelAttribute SaledBookDto saledBookDto) {
		log.info("======================paymentMyCartList===================");

		MyPointListDto myPointListDto = new MyPointListDto();

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.paymentMyCart(saledBookDto, loginedMemberDto.getM_id());

		if (result > 0) {
			myPointListDto.setM_id(loginedMemberDto.getM_id());
			myPointListDto.setPl_payment_book_point(saledBookDto.getSb_all_price());
			myPointListDto.setPl_desc("도서 " + saledBookDto.getSb_book_count() + "권 구매");

            mypageService.nowBooks(saledBookDto.getSb_book_count(), saledBookDto.getB_count(), saledBookDto.getB_no());
            memberService.refreshPoint(session);
			result = mypageService.insertPoint(myPointListDto);

			if (result > 0) {
				result = mypageService.deletePaymentMyCart(loginedMemberDto.getM_id(), saledBookDto.getB_no());

				if (result > 0) {
					result = Config.DELETE_PAYMENT_CART_SUCCESS;
				} else {
					result = Config.DELETE_PAYMENT_CART_FAIL;
				}
			} else {
				result = Config.INSERT_POINT_FAIL;
			}
		} else {
			result = Config.PAYMENT_FAIL;
		}

		if (result == Config.DELETE_PAYMENT_CART_SUCCESS) {
			result = Config.PAYMENT_SUCCESS;
		}

		model.addAttribute("result", result);
		return "result";
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
	 * 장바구니 모두 결제 버튼 클릭
	 */
	@GetMapping("/all_payment_form")
	public String allPaymentMyCartForm(Model model, HttpSession session) {
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		List<MemberCartDto> memberCartDtos = mypageService.allPaymentForm(loginedMemberDto.getM_id());

		int allPrice = mypageService.calcAllPrice(memberCartDtos);
		int discount = mypageService.memberDiscount(allPrice, loginedMemberDto.getM_grade());
        int currentPoint = mypageService.currentPoint(session);
        
		model.addAttribute("memberCartDtos", memberCartDtos);
		model.addAttribute("allPrice", allPrice);
		model.addAttribute("discount", discount);
		model.addAttribute("finalPrice", allPrice - discount + 3000);
        model.addAttribute("currentPoint", currentPoint);

		return "mypage/all_payment_form";
	}

	/*
	 * 도서 상세 페이지에서 장바구니 버튼 클릭
	 */
	@GetMapping("/add_cart")
	public String moveMyCart(HttpSession session, Model model, @RequestParam("b_no") int b_no) {

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.addMyCart(loginedMemberDto.getM_id(), b_no);

		if (result > 0) {
			result = Config.ADD_CART_SUCCESS;
		} else {
			result = Config.ADD_CART_FAIL;
		}
		memberService.refreshPoint(session);
		model.addAttribute("result", result);
		return "result";
	}

	/*
	 * 모두 결제 폼에서 결제하기 버튼 클릭
	 */
	@PostMapping("/all_payment_form_confirm")
	public String allPaymentMyCartList(HttpSession session, Model model, @ModelAttribute SaledBookDto saledBookDto) {
		log.info("<=====================allPaymentMyCartList==================>");

		MyPointListDto myPointListDto = new MyPointListDto();

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.allPaymentMyCartList(loginedMemberDto.getM_id(), saledBookDto);

		if (result > 0) {
			myPointListDto.setM_id(loginedMemberDto.getM_id());
			myPointListDto.setPl_payment_book_point(saledBookDto.getSb_all_price());

			result = mypageService.insertAllPoint(myPointListDto, loginedMemberDto.getM_id());
			log.info("saledBookDto.getB_count() ==================>" + saledBookDto.getB_count());
			if (result > 0) {
				result = mypageService.deleteAllMyCart(loginedMemberDto.getM_id());

				if (result > 0) {
					result = Config.DELETE_PAYMENT_CART_SUCCESS;
				} else {
					result = Config.DELETE_PAYMENT_CART_FAIL;
				}
			} else {
				result = Config.INSERT_POINT_FAIL;
			}
		}

		if (result == Config.DELETE_PAYMENT_CART_SUCCESS) {
			result = Config.PAYMENT_SUCCESS;
		}
		memberService.refreshPoint(session);
		model.addAttribute("result", result);
		return "result";
	}

	/*
	 * 도서 상세 페이지에서 바로 결제 버튼 클릭
	 */
	@GetMapping("/move_payment")
	public String movePayment(HttpSession session, Model model, @RequestParam("b_no") int b_no) {

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);
		model.addAttribute(Config.LOGINED_MEMBER_INFO, loginedMemberDto);

		BookDto bookDto = mypageService.setView(b_no);
		model.addAttribute("bookDto", bookDto);

		int allPrice = bookDto.getB_price();
		int discount = mypageService.memberDiscount(allPrice, loginedMemberDto.getM_grade());

		model.addAttribute("allPrice", allPrice);
		model.addAttribute("discount", discount);
		model.addAttribute("finalPrice", allPrice - discount + 3000);

		return "mypage/move_payment_form";
	}

	/*
	 * 도서 구매 내역 페이지 이동
	 */
	@GetMapping("/payment_list_form")
	public String getPaymentList(HttpSession session, Model model) {
		log.info("getPaymentList");

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		List<SaledBookDto> saledBookDtos = mypageService.getPaymentList(loginedMemberDto.getM_id());
		model.addAttribute("saledBookDtos", saledBookDtos);

		return "mypage/payment_list_form";
	}

	/*
	 * 주문 취소
	 */
	@GetMapping("/delete_payment_list_form")
	public String deletePaymentList(HttpSession session, 
														@RequestParam("sb_no") int sb_no, 
														@RequestParam("b_no") int b_no) {
        // #TODO RESULT 페이지로 이동
		String nextPage = "redirect:/mypage/payment_list_form";

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);
		
		int result = mypageService.deleteMyPaymentList(loginedMemberDto.getM_id(), sb_no, b_no);
		
        // #TODO result 세팅 필요
//		if (result <= 0) {
//            // 실패
//			log.info("Delete Fail");
//		}

		return nextPage;
	}
	
	/*
	 * 반품 요청
	 */
	@GetMapping("/return_payment_list_form")
	public String returnPaymentList(HttpSession session, Model model) {
		log.info("returnPaymentList");
		
        // #TODO RESULT 처리 필요
		String nextPage = "redirect:/mypage/payment_list_form";
		
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

        // #TODO RESULT 처리 필요
		return nextPage;
	}
	
	/*
	 * 찜 목록 페이지 이동
	 */
	@GetMapping("/member_pick")
	public String myPickList(Model model, HttpSession session) {
		log.info("myPickList");

        // #TODO RESULT 처리 필요
		String nextPage = "mypage/member_pick_form";

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		List<MemberPickDto> memberPickDtos = mypageService.myPickList(loginedMemberDto.getM_id());
		

		model.addAttribute("memberPickDtos", memberPickDtos);

        // #TODO RESULT 처리 필요
		return nextPage;
	}
	
	/*
	 * 도서 상세 페이지 찜 버튼 클릭
	 */
	@GetMapping("/add_member_pick")
	public String addMyPick(HttpSession session, @RequestParam("b_no") int b_no) {
		log.info("addMyPick");

        // #TODO RESULT 처리 필요
		String nextPage = "/mypage/member_pick_form";

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int result = mypageService.addMyPick(loginedMemberDto.getM_id(), b_no);
        // #TODO RESULT 처리 필요
		if (result < 0) {
			log.info("add pick Fail");
		}
		
        // #TODO RESULT 처리 필요
		return nextPage;
	}
	
	/*
	 *  찜 제거
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
		
//        // #TODO RESULT 처리 필요
//		String nextPage = "redirect:/mypage/member_pick_form";
//
//		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);
//
//		int result = mypageService.addMyPick(loginedMemberDto.getM_id(), b_no);
//        // #TODO RESULT 처리 필요
//		if (result < 0) {
//			log.info("add pick Fail");
//		}
//		
//        // #TODO RESULT 처리 필요
//		return nextPage;
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
		myPointListDto.setPl_desc("포인트 충전");

		int result = mypageService.chargePoint(myPointListDto);

		model.addAttribute("result", result);	
		return "result";
	}
}