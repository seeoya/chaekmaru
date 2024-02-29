package com.maru.chaekmaru;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;


@Controller
@RequestMapping
@Log4j2
public class MainController {
	
	String nextPage = "";
	
	@GetMapping({"/", ""})
	public String index() {
		log.info("index");
		
		Test test = new Test();
		test.sss();
		
		nextPage = "index";
		
		return nextPage;
	}

	

}
