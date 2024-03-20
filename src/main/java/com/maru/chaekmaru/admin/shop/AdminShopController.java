package com.maru.chaekmaru.admin.shop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.book.ListPageDto;
import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.member.MemberDto;
import com.maru.chaekmaru.mypage.MyPointListDto;
import com.maru.chaekmaru.shop.SaledBookDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/admin/shop")
public class AdminShopController {
	
	
	@Autowired
	AdminShopService adminShopService;
	
	//회원 포인트 리스트
	
	@GetMapping("/point_list_form")
	public String pointListForm(Model model) {
		log.info("pointListForm()");
		
		String nextPage = "admin/shop/point_list_form";
		
		List<MyPointListDto> pointListDtos = adminShopService.pointListForm();
		
		model.addAttribute("pointListDtos", pointListDtos);
		
		return nextPage;		
		
	}
	
	//회원 포인트 관리 Form
	
	@GetMapping("/point_management_form")
	public String pointManagementForm(@RequestParam("m_id") String m_id, Model model) {
		log.info("pointManagementForm()");
		
		String nextPage = "admin/shop/point_management_form";
		
		MyPointListDto myPointDto = adminShopService.pointManagementForm(m_id);
		
		model.addAttribute("myPointDto", myPointDto);
		
		return nextPage;		
		
	}
	
	
	//회원 포인트 수정
	
	@PostMapping("/modify_point_confirm")
	public String modifyPointConfirm(MyPointListDto myPointListDto) {
		log.info("modifyPointConfirm");

		String nextPage = "redirect:/admin/shop/point_list_form";
		
		adminShopService.modifyPointConfirm(myPointListDto);
		
		return nextPage;	
		
	}
	
	// 회원별 포인트 내역
	
	@GetMapping("/point_history_form")
	public String pointHistoryForm(@RequestParam("m_id") String m_id, Model model) {
		log.info("pointHistoryForm()");
		
		String nextPage = "admin/shop/point_history_form";		
		
		List<MyPointListDto> myPointDtos = adminShopService.pointHistoryForm(m_id);
		
		model.addAttribute("myPointDtos", myPointDtos);
		
		return nextPage;		
		
	}
	
	
	// 회원계정 관리 Form
	
	@GetMapping("/user_account_active_form")
	public String userAccountActiveForm(Model model) {
		log.info("userAccountActiveForm()");
		
		String nextPage = "admin/shop/user_list_form";
		
		List<MemberDto> userDtos = adminShopService.userAccountActiveForm();
		
		model.addAttribute("userDtos", userDtos);
		
		return nextPage;		
		
	}
	
	
	// 회원계정 비활성화
	
	@PostMapping("/user_account_active_confirm")
	public String userAccountActiveConfirm(@RequestParam(value = "m_id") String m_id, Model model) {
		log.info("userAccountActiveConfirm()");
		
		String nextPage = "admin/result";
		
		int result = adminShopService.userAccountActiveConfirm(m_id);
		
		switch (result) {
		case Config.USER_ACCOUNT_UNACTIVE_SUCCESS: 
			model.addAttribute("result", Config.USER_ACCOUNT_UNACTIVE_SUCCESS);
			break;

		case Config.USER_ACCOUNT_UNACTIVE_FAIL: 
			model.addAttribute("result", Config.USER_ACCOUNT_UNACTIVE_FAIL);
			break;
		
		}
		
		return nextPage;		
	}
	

	// 판매 관리
	
	@GetMapping("/sale_management_form")
	public String saleManagementForm() {
		log.info("sale_management_form()");
		
		String nextPage = "admin/shop/sale_management_form";
	
		return nextPage;		
		
	}

	// 판매된 도서 리스트
	
	@GetMapping("/saled_book_list_form")
	public String saledBookListForm(Model model) {
		log.info("saledBookListForm()");
		
		String nextPage = "admin/shop/saled_book_list_form";
		
		List<SaledBookDto> saledBookDtos = adminShopService.saledBookListForm();
		List<AdminShopDto> orderCnts = adminShopService.saledBookOrderCnt();
		
		log.info("orderCnts: " + orderCnts.size());
		log.info("orderCnts: " + orderCnts.toString());
		
		model.addAttribute("saledBookDtos", saledBookDtos);
		model.addAttribute("orderCnts", orderCnts);
		
		return nextPage;		
		
	}
	
	
	// 기간별 판매된 도서 리스트
	
		@GetMapping("/saled_books_by_period")
		public String saledBooksByPeriod(@RequestParam("saled_start") String saled_start,
										@RequestParam("saled_end") String saled_end, Model model) {
			log.info("saledBooksByPeriod()");
			
			String nextPage = "admin/shop/saled_book_list_form";
			
			saled_start = saled_start.replaceAll("-", "").substring(2);
			saled_end = saled_end.replaceAll("-", "").substring(2);
			
			List<SaledBookDto> saledBookDtos = adminShopService.saledBooksByPeriod(saled_start, saled_end);
			
			model.addAttribute("saledBookDtos", saledBookDtos);
			
			return nextPage;		
			
		}
	
	
	// 판매도서 상세 내역
	
	@GetMapping("/saled_detail_form")
	public String saledDetailForm(@RequestParam("sb_no") int sb_no, Model model) {
		log.info("saledBookListForm()");
		
		String nextPage = "admin/shop/saled_detail_form";
		
		SaledBookDto saledBookDto = adminShopService.saledDetailForm(sb_no);
		
		model.addAttribute("saledBookDto", saledBookDto);
		
		return nextPage;		
		
	}
	

	// 반품 도서 리스트
	
	@GetMapping("/return_book_list_form")
	public String returnBookListForm(Model model) {
		log.info("returnBookListForm()");
		
		String nextPage = "admin/shop/return_book_list_form";
		
		List<SaledBookDto> returnBookDtos = adminShopService.returnBookListForm();

		model.addAttribute("returnBookDtos", returnBookDtos);
		model.addAttribute("active", "form");
		
		return nextPage;		
		
	}
		
	
	// 반품 도서 이력 리스트
	
		@GetMapping("/return_book_history_list")
		public String returnBookHistoryList(Model model) {
			log.info("returnBookHistoryList()");
			
			String nextPage = "admin/shop/return_history_list_form";
			
			List<SaledBookDto> returnBookDtos = adminShopService.returnBookHistoryList();

			model.addAttribute("returnBookDtos", returnBookDtos);
			model.addAttribute("active", "list");
			
			return nextPage;		
			
		}
		
		
	// 반품 도서 이력 상세 내역
	
	@GetMapping("/return_book_detail_form")
	public String returnBookDetailForm(@RequestParam("sb_no") int sb_no, Model model) {
		log.info("returnBookDetailForm()");
		
		String nextPage = "admin/shop/return_book_detail_form";
		
		SaledBookDto returnBookDto = adminShopService.returnBookDetailForm(sb_no);
		
		model.addAttribute("returnBookDto", returnBookDto);
		
		return nextPage;		
		
	}
	
	@GetMapping("/return_history_detail_form")
	public String returnHistoryDetailForm(@RequestParam("sb_no") int sb_no, Model model) {
		log.info("returnBookDetailForm()");
		
		String nextPage = "admin/shop/return_history_detail_form";
		
		SaledBookDto returnBookDto = adminShopService.returnBookDetailForm(sb_no);
		
		model.addAttribute("returnBookDto", returnBookDto);
		
		return nextPage;		
		
	}
	// 반품도서 승인
	
	@GetMapping("/return_approval_confirm")
	public String returnApprovalConfirm(@RequestParam("sb_no") int sb_no,
										@RequestParam("b_no") int b_no,
										@RequestParam("sb_book_count") int sb_book_count) {
		log.info("returnApprovalConfirm()");
		
		String nextPage = "redirect:/admin/shop/return_book_list_form";
		
		adminShopService.returnApprovalConfirm(sb_no, b_no, sb_book_count);
		adminShopService.retrunPoint(sb_no);
		
		return nextPage;		
	}


	// 반품도서 승인불가

	@GetMapping("/return_notapproved_confirm")
	public String returnNotApprovedConfirm(@RequestParam("sb_no") int sb_no) {
		log.info("returnNotApprovedConfirm()");
		
		String nextPage = "redirect:/admin/shop/return_book_list_form";
		
		adminShopService.returnNotApprovedConfirm(sb_no);
			
		return nextPage;		
	}
	
	
	// 도서 재고 리스트
	
	@GetMapping("/book_inventory_list_form")
	public String bookInventoryListForm(@RequestParam(required = false, value = "page", defaultValue = "1") String page, Model model) {
		log.info("bookInventoryListForm()");
		
		int pageItemPerPage = 20;
		int nowPageCount = Integer.parseInt(page);
		int allBookCount = adminShopService.countBook();
		int allPageCount = adminShopService.countAllPage(allBookCount, pageItemPerPage);

		if(nowPageCount > allPageCount) {
			nowPageCount = allPageCount;
		}
		
		ArrayList<BookDto> items = new ArrayList<>();
		ArrayList<ListPageDto> listPageDtos = new ArrayList<>();
	
		items = adminShopService.setList(pageItemPerPage, nowPageCount);
		listPageDtos = adminShopService.setPaging(pageItemPerPage, nowPageCount, allPageCount);
		
		model.addAttribute("nowPage", nowPageCount);
		model.addAttribute("allPage", allPageCount);

		model.addAttribute("listPageDtos", listPageDtos);
		model.addAttribute("items", items);

		
		String nextPage = "admin/shop/book_inventory_list_form";	
		
		return nextPage;		
		
	}
	
	
	// 도서 재고 수정 FORM	
	
	@GetMapping("/modify_book_inventory_form")
	public String modifyBookInventoryForm(@RequestParam("b_no") int b_no, Model model) {
		log.info("modifyBookInventoryForm()");
		
		String nextPage = "admin/shop/modify_book_inventory_form";
		
		BookDto bookDto = adminShopService.modifyBookInventoryForm(b_no);
		
		model.addAttribute("bookDto", bookDto);
		
		return nextPage;		
		
	}
	
	
	// 도서 재고 수정

	@PostMapping("/modify_book_inventory_confirm")
	public String modifyBookInventoryConfirm(BookDto bookDto) {
		log.info("modifyBookInventoryConfirm()");
		
		String nextPage = "redirect:/admin/shop/book_inventory_list_form";
		
		adminShopService.modifyBookInventoryConfirm(bookDto);
			
		return nextPage;		
	}

	
}
	



