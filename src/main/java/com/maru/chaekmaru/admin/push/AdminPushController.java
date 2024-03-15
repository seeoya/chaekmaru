package com.maru.chaekmaru.admin.push;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/admin/push")
public class AdminPushController {
			
		@Autowired
		AdminPushService adminPushService;
		
		
	    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
	    
		private String showMsgAndRedirect(AdminPushDto params, Model model) {
	    	log.info("showMsgAndRedirect()");
	    	
	        model.addAttribute("params", params);
	        return "admin/push/msgRedirect";
	    }
}
	

	
