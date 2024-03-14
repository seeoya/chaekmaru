package com.maru.chaekmaru.admin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maru.chaekmaru.admin.board.AdminBoardDto;
import com.maru.chaekmaru.admin.board.AdminBoardService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminHomeController {
	
	@GetMapping({"/", ""})
	public String home() {
		log.info("home()");
		
	/*	@Autowired
		AdminBoardService adminBoardService;
		
		@GetMapping({"/", ""})
		public String adminBoardForm(Model model) {
			log.info("adminBoardForm()");
					
			ArrayList<AdminBoardDto> salesdaily = adminBoardService.totalSalesDaily();
			ArrayList<AdminBoardDto> salesCateDaily = adminBoardService.salesCateDaily();
					
			model.addAttribute("salesdaily", salesdaily);
			model.addAttribute("salesCateDaily", salesCateDaily);*/
			
			String nextPage = "admin/index";
			
			return nextPage;		
	}

	@GetMapping("/result")
	public String test(HttpSession session, Model model, @RequestParam( value = "result", defaultValue = "0") String result) {
		int resultNum = Integer.parseInt(result);
		
		model.addAttribute("result", resultNum);

		return "result";
	}
}
