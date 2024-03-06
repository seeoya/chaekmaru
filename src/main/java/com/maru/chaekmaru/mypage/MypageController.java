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

import com.maru.chaekmaru.member.MemberDto;

import com.maru.chaekmaru.shop.SaledBookDto;
import com.maru.chaekmaru.review.ReviewDto;
import com.maru.chaekmaru.review.ReviewService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;


@Log4j2
@Controller
@RequestMapping("/mypage")
public class MypageController {
	
	@Autowired
	MypageService mypageService;
	
	@Autowired
	ReviewService reviewService;
	
	/*
	 * 장바구니 목록
	 */
	@GetMapping("/member_cart_form")
	public String myCartList(Model model, HttpSession session) {
		log.info("myCartList");
		
		String nextPage = "mypage/member_cart_form";
		
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
		
		List<MemberCartDto> memberCartDtos = mypageService.getMyCartList(loginedMemberDto.getM_id());
		
		model.addAttribute("memberCartDtos", memberCartDtos);
		
		return nextPage;
		
	}
	
	/*	
	 * 장바구니에서 수량 변경 클릭
	 */
	@PostMapping("/cart_modify_form")
	public String cartmodifyform(HttpSession session, Model model, @RequestParam("c_no") int c_no, @RequestParam("c_book_count") int c_book_count) {
		log.info("cartmodifyform");
		
		String nextPage = "redirect:/mypage/member_cart_form";
		
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
		
		int result = mypageService.addBookCount(loginedMemberDto.getM_id(), c_no, c_book_count);
		if (result < 0) {
			nextPage = "/";
		}
		
		return nextPage;
		
	}
	
//	/*	
//	 * 도서 상세 페이지 장바구니 클릭
//	 */
//	@PostMapping("/member_cart_form")
//	public String addBookNumber(HttpSession session) {
//		log.info("addMyCart");
//		
//		String nextPage = "mypage/member_cart_form";
//		
//		MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
//		
//		int result = mypageService.addMyCart(loginedMemberDto.getM_id());
//		if (result < 0) {
//			return nextPage;
//		}
//		
//		return nextPage;
//		
//	}
	
	/*
	 * 마이 페이지 장바구니 삭제 클릭
	 */
	@GetMapping("/delete_mycart_confirm")
	public String deleteMyCart(HttpSession session, @RequestParam("c_no") int c_no) {
		log.info("deleteMyCartList");
		
		String nextPage = "redirect:/mypage/member_cart_form";
		
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
		
		int result = mypageService.deleteMyCart(loginedMemberDto.getM_id(), c_no);
		
		if (result <= 0) {
			log.info("Delete Fail");
//			nextPage = "/fail";
		}
		
		return nextPage;
		
	}
	
	/*
	 * 결제하기
	 */
	@GetMapping("/payment_form")
	public String paymentMyCartForm(Model model, HttpSession session, @RequestParam("c_no") int c_no) {
		log.info("paymentMyCartListForm");
		
		String nextPage = "mypage/payment_form";
		
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
		
		List<MemberCartDto> memberCartDtos = mypageService.paymentForm(loginedMemberDto.getM_id(), c_no);
		
		model.addAttribute("memberCartDtos", memberCartDtos);
		
		return nextPage;
		
	}
	
	/*
	 * 결제 폼에서 결제하기 버튼 클릭
	 */
	@PostMapping("/payment_form_confirm")
	public String paymentMyCartList(HttpSession session,
								@ModelAttribute SaledBookDto saledBookDto) {
		log.info("======================paymentMyCartList===================");
		
		log.info(saledBookDto.getSb_name());
		log.info(saledBookDto.getSb_all_price());
		
		String nextPage = "mypage/payment_form_confirm";
		
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
		
		int result = mypageService.paymentMyCart(saledBookDto, loginedMemberDto.getM_id());
		
		return nextPage;
		
	}
	
	@GetMapping("/my_review")
	public String myReview(Model model, HttpSession session) {
		String nextPage = "/mypage/my_review";
		
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
		
		log.info(loginedMemberDto.getM_id());
		ArrayList<ReviewDto> myReviews =  reviewService.setMyReview(loginedMemberDto.getM_id());
		model.addAttribute("reviews", myReviews);
		
		return nextPage;
	}
	
}
