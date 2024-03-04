package com.maru.chaekmaru;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.book.BookService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping
@Log4j2
public class MainController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	BookService bookService;

	String nextPage = "";

	@GetMapping({ "/", "" })
	public String index(Model model) {
		nextPage = "index";

		ArrayList<BookDto> recommendBookDtos  = bookService.recommendItem();
		model.addAttribute("recommend", recommendBookDtos);

		ArrayList<BookDto> bestBookDtos = bookService.bestItem(5);
		model.addAttribute("best", bestBookDtos);

		ArrayList<BookDto> newBookDtos = bookService.newItem(5);
		model.addAttribute("news", newBookDtos);

		return nextPage;
	}

	@GetMapping("/styleguide")
	public String styleguide(Model model) {
		nextPage = "styleguide";

		return nextPage;
	}

}
