package com.maru.chaekmaru.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminHomeController {
	
	@GetMapping({"/", ""})
	public String home() {
		log.info("home()");
		
		String nextPage = "/admin/index";
		
		return nextPage;
	}

	@GetMapping("/result")
	public String test(HttpSession session, Model model, @RequestParam( value = "result", defaultValue = "0") String result) {
		int resultNum = Integer.parseInt(result);
		
		model.addAttribute("result", resultNum);

		return "result";
	}
}
