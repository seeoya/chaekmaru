package com.maru.chaekmaru.admin.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/admin/board")
public class AdminBoardController {

	@Autowired
	AdminBoardService adminBoardService;
	
	@GetMapping("/admin_board_form")
	public String adminBoardForm() {
		log.info("adminBoardForm()");
		
		
		//ArrayList<> total_sales_weekly = adminBoardService.sales_weekly();
		
		
		String nextPage = "/admin/book/admin_board_form";
		
		return nextPage;		
		
	}
	
}
