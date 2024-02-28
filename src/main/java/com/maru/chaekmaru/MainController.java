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
        log.info("index1111");
		
		nextPage = "index";
		
		return nextPage;
	}
	
	
	@GetMapping( "**")
	public String error404() {
		log.info("404 ERROR");
		
		nextPage = "404";
		
		return nextPage;
	}

}
