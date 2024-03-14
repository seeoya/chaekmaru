package com.maru.chaekmaru.admin.board;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.book.ListPageDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/admin/board")
public class AdminBoardController {

	@Autowired
	AdminBoardService adminBoardService;
	
	@GetMapping("/admin_board_form")
	public String adminBoardForm(Model model) {
		log.info("adminBoardForm()");
				
		ArrayList<AdminBoardDto> salesdaily = adminBoardService.totalSalesDaily();
		ArrayList<AdminBoardDto> salesCateDaily = adminBoardService.salesCateDaily();
				
		model.addAttribute("salesdaily", salesdaily);
		model.addAttribute("salesCateDaily", salesCateDaily);
		
		String nextPage = "/admin/book/admin_board_form";
		
		return nextPage;		
		
	}
	
}
