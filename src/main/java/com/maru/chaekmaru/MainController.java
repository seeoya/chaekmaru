package com.maru.chaekmaru;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping
@Log4j2
public class MainController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	String nextPage = "";

	@GetMapping({ "/", "" })
	public String index(Model model) {
		nextPage = "index";

		return nextPage;
	}

	@GetMapping("/styleguide")
	public String styleguide(Model model) {
		nextPage = "styleguide";

		return nextPage;
	}

}
