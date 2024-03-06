package com.maru.chaekmaru.mypage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maru.chaekmaru.member.MemberDto;
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
	 * 도서 상세 페이지 장바구니 클릭
	 */
	@PostMapping("/member_cart_form")
	public String addMyCart(HttpSession session) {
		log.info("addMyCart");
		
		String nextPage = "mypage/member_cart_form";
		
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
		
		int result = mypageService.addMyCart(loginedMemberDto.getM_id());
		if (result < 0) {
			return nextPage;
		}
		
		return nextPage;
		
	}
	
	/*
	 * 마이 페이지 장바구니 삭제 클릭
	 */
	@GetMapping("/member_cart_delete_confirm")
	public String deleteMyCart(Model model, HttpSession session) {
		log.info("deleteMyCartList");
		
		String nextPage = "redirect:/mypage/member_cart_form";
		
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
		
		int result = mypageService.deleteMyCart(loginedMemberDto.getM_id());
		if (result < 0) {
			nextPage = "/faill";
		}
		
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
