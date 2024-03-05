package com.maru.chaekmaru.admin.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maru.chaekmaru.admin.member.AdminMemberDto;
import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.shop.SaledBookDto;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/admin/book")
public class AdminBookController {
			
		@Autowired
		AdminBookService adminBookService;
		
		@GetMapping("/regist_book_form")
		public String registBookForm() {
			log.info("registBookForm()");
			
			String nextPage = "/admin/book/regist_book_form";
			
			return nextPage;		
			
		}
		
		@PostMapping("/regist_book_confirm")
		public String registBookConfirm(BookDto bookDto, Model model) {
			log.info("registBookConfirm()");
			
			String nextPage = "result";
			
			int result = adminBookService.registBookConfirm(bookDto);
			
			switch (result) {
			case Config.REGIST_BOOK_SUCCESS_AT_DATABASE: 
				model.addAttribute("result", Config.REGIST_BOOK_SUCCESS_AT_DATABASE);
				break;

			case Config.BOOK_ALREADY_EXIST: 
				model.addAttribute("result", Config.BOOK_ALREADY_EXIST);
				break;
			
			case Config.REGIST_BOOK_FAIL_AT_DATABASE: 
				model.addAttribute("result", Config.REGIST_BOOK_FAIL_AT_DATABASE);
				break;

			}
								
			return nextPage;		
			
		}
		
		@GetMapping("/book_list_form")
		public String bookListForm(Model model) {
			log.info("bookListForm()");
			
			String nextPage = "admin/book/book_list_form";
			
			List<BookDto> bookListDtos = adminBookService.bookListForm();
			
			model.addAttribute("bookListDtos", bookListDtos);
					
			return nextPage;		
			
		}
		
		@GetMapping("/modify_book_form")
		public String modifyBookForm(@RequestParam("b_no") int b_no, Model model) {
			log.info("modifyBookForm()");
			
			String nextPage = "/admin/book/modify_book_form";
			
			BookDto  bookDto = adminBookService.modifyBookForm(b_no);
			
			model.addAttribute("bookDto", bookDto);
			
			return nextPage;		
			
		}
		
		@PostMapping("/modify_book_confirm")
		public String modifyBookConfirm(BookDto bookDto, Model model) {
			log.info("modifyBookConfirm()");
			
			String nextPage = "result";
					
			int result = adminBookService.modifyBookConfirm(bookDto);
					
			if(result == Config.MODIFY_BOOK_SUCCESS_AT_DATABASE) {
								
				model.addAttribute("result", Config.MODIFY_BOOK_SUCCESS_AT_DATABASE);
			
			} else 
	 		
				model.addAttribute("result", Config.MODIFY_BOOK_FAIL_AT_DATABASE);
			
			
			return nextPage;			
					
		}
		
		@GetMapping("/delete_book_confirm")
		public String deleteBookConfirm(@RequestParam("b_no") int b_no) {
			log.info("deleteBookConfirm()");
			
			String nextPage = "redirect:/admin/book/book_list_form";
			
			adminBookService.deleteBookConfirm(b_no);
			
			return nextPage;		
				
		}
}
	

	
