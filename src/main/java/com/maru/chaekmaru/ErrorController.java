package com.maru.chaekmaru;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
	
	  @GetMapping("/error")
	    public String handleError(HttpServletRequest request) {
		  
	        return "404";
	    }
	
}
