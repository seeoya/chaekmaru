package com.maru.chaekmaru;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.book.BookService;
import com.maru.chaekmaru.member.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping
@Log4j2
public class MainController {

	@Autowired
	BookService bookService;

	@Autowired
	MemberService memberService;

	String nextPage = "";

	@GetMapping({ "/", "" })
	public String index(HttpSession session, Model model) {
		memberService.refreshMemberInfo(session);

		ArrayList<BookDto> recommendBookDtos = bookService.recommendItem(9);
		model.addAttribute("recommend", recommendBookDtos);

		ArrayList<BookDto> bestBookDtos = bookService.bestItem(10);
		model.addAttribute("best", bestBookDtos);

		ArrayList<BookDto> newBookDtos = bookService.newItem(10);
		model.addAttribute("news", newBookDtos);

		return "index";
	}

	@GetMapping("/styleguide")
	public String styleguide(Model model) {
        return "styleguide";
	}

	@GetMapping("/result")
	public String test(HttpSession session, Model model, @RequestParam( value = "result", defaultValue = "0") String result) {
		int resultNum = Integer.parseInt(result);
		
		model.addAttribute("result", resultNum);

		return "result";
	}
	
	@GetMapping("/test")
	public String test(HttpSession session, Model model) {

		return "test";
	}
}
