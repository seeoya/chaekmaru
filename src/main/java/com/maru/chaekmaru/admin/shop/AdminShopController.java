package com.maru.chaekmaru.admin.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.member.MemberDto;
import com.maru.chaekmaru.mypage.MyPointListDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/admin/shop")
public class AdminShopController {
	
	
	@Autowired
	AdminShopService adminShopService;
	
	@GetMapping("/point_list_form")
	public String pointListForm(Model model) {
		log.info("pointListForm()");
		
		String nextPage = "admin/shop/point_list_form";
		
		List<MyPointListDto> pointListDtos = adminShopService.pointListForm();
		
		model.addAttribute("pointListDtos", pointListDtos);
		
		return nextPage;		
		
	}
	
	@GetMapping("/point_management_form")
	public String pointManagementForm(@RequestParam("m_id") String m_id, Model model) {
		log.info("pointManagementForm()");
		
		String nextPage = "admin/shop/point_management_form";
		
		MyPointListDto myPointDto = adminShopService.pointManagementForm(m_id);
		
		model.addAttribute("myPointDto", myPointDto);
		
		return nextPage;		
		
	}
	
	
	
	@PostMapping("/modify_point_confirm")
	public String modifyPointConfirm(MyPointListDto myPointListDto) {
		log.info("modifyPointConfirm");

		String nextPage = "redirect:/admin/shop/point_list_form";
		
		adminShopService.modifyPointConfirm(myPointListDto);
		
		return nextPage;	
		
	}
	
	@GetMapping("/point_history_form")
	public String pointHistoryForm(@RequestParam("m_id") String m_id, Model model) {
		log.info("pointHistoryForm()");
		
		String nextPage = "admin/shop/point_history_form";		
		
		List<MyPointListDto> myPointDtos = adminShopService.pointHistoryForm(m_id);
		
		model.addAttribute("myPointDtos", myPointDtos);
		
		return nextPage;		
		
	}
	
	@GetMapping("/user_account_active_form")
	public String userAccountActiveForm(Model model) {
		log.info("userAccountActiveForm()");
		
		String nextPage = "admin/shop/user_list_form";
		
		List<MemberDto> userDtos = adminShopService.userAccountActiveForm();
		
		model.addAttribute("userDtos", userDtos);
		
		return nextPage;		
		
	}
	
	@GetMapping("/user_account_active_confirm")
	public String userAccountActiveConfirm(@RequestParam("m_id") String m_id, Model model) {
		log.info("userAccountActiveConfirm()");
		
		String nextPage = "result";
		
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
	
}
