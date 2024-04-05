package com.maru.chaekmaru.admin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.maru.chaekmaru.admin.board.AdminBoardDto;
import com.maru.chaekmaru.admin.board.AdminBoardService;
import com.maru.chaekmaru.admin.board.AdminMailDto;
import com.maru.chaekmaru.admin.member.AdminMemberDto;
import com.maru.chaekmaru.admin.util.UploadFileService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminHomeController {
	
	@Autowired
	AdminBoardService adminBoardService;
	
	@Autowired
	UploadFileService uploadFileService;
	
	
	@GetMapping({"/", ""})
	public String home(HttpSession session, Model model) {
		log.info("home()");
			
			String nextPage = "admin/index";
			
			AdminMemberDto loginedAdminMemberDto = (AdminMemberDto) session.getAttribute("loginedAdminMemberDto");
			if(loginedAdminMemberDto != null) {
				
				ArrayList<AdminBoardDto> salesDaily = adminBoardService.totalSalesDaily();				
				ArrayList<AdminBoardDto> salesCateMonth = adminBoardService.salesCateMonth();
				ArrayList<AdminBoardDto> salesCateMonthThis = adminBoardService.salesCateMonthThis();
				
				log.info("salesCateMonth4===" +salesCateMonth.get(0).getSc_c_sales4());
				log.info("salesCateMonth7===" +salesCateMonth.get(0).getSc_c_sales7());
				
				model.addAttribute("sales1", salesDaily.get(0).getSc_sales1());
				model.addAttribute("sales2", salesDaily.get(0).getSc_sales2());
				model.addAttribute("sales3", salesDaily.get(0).getSc_sales3());
				model.addAttribute("sales4", salesDaily.get(0).getSc_sales4());
				model.addAttribute("sales5", salesDaily.get(0).getSc_sales5());
				model.addAttribute("sales6", salesDaily.get(0).getSc_sales6());
				model.addAttribute("sales7", salesDaily.get(0).getSc_sales7());			

				model.addAttribute("date1", salesDaily.get(0).getSc_date1());
				model.addAttribute("date2", salesDaily.get(0).getSc_date2());
				model.addAttribute("date3", salesDaily.get(0).getSc_date3());
				model.addAttribute("date4", salesDaily.get(0).getSc_date4());
				model.addAttribute("date5", salesDaily.get(0).getSc_date5());
				model.addAttribute("date6", salesDaily.get(0).getSc_date6());
				model.addAttribute("date7", salesDaily.get(0).getSc_date7());
				
					
				model.addAttribute("cate0", salesCateMonth.get(0).getSc_c_sales0());
				model.addAttribute("cate1", salesCateMonth.get(0).getSc_c_sales1());
				model.addAttribute("cate2", salesCateMonth.get(0).getSc_c_sales2());
				model.addAttribute("cate3", salesCateMonth.get(0).getSc_c_sales3());
				model.addAttribute("cate4", salesCateMonth.get(0).getSc_c_sales4());
				model.addAttribute("cate5", salesCateMonth.get(0).getSc_c_sales5());
				model.addAttribute("cate6", salesCateMonth.get(0).getSc_c_sales6());
				model.addAttribute("cate7", salesCateMonth.get(0).getSc_c_sales7());
				model.addAttribute("cate8", salesCateMonth.get(0).getSc_c_sales8());
				model.addAttribute("cate9", salesCateMonth.get(0).getSc_c_sales9());		
				model.addAttribute("c_date1", salesCateMonth.get(0).getSc_c_date1());		

				model.addAttribute("cate10", salesCateMonthThis.get(0).getSc_c_sales10());
				model.addAttribute("cate11", salesCateMonthThis.get(0).getSc_c_sales11());
				model.addAttribute("cate12", salesCateMonthThis.get(0).getSc_c_sales12());
				model.addAttribute("cate13", salesCateMonthThis.get(0).getSc_c_sales13());
				model.addAttribute("cate14", salesCateMonthThis.get(0).getSc_c_sales14());
				model.addAttribute("cate15", salesCateMonthThis.get(0).getSc_c_sales15());
				model.addAttribute("cate16", salesCateMonthThis.get(0).getSc_c_sales16());
				model.addAttribute("cate17", salesCateMonthThis.get(0).getSc_c_sales17());
				model.addAttribute("cate18", salesCateMonthThis.get(0).getSc_c_sales18());
				model.addAttribute("cate19", salesCateMonthThis.get(0).getSc_c_sales19());
				model.addAttribute("c_date2", salesCateMonthThis.get(0).getSc_c_date2());
				
				
			}
			
			return nextPage;		
	}

	@GetMapping("/result")
	public String test(HttpSession session, Model model, @RequestParam( value = "result", defaultValue = "0") String result) {
		
		String nextPage = "admin/result";
		int resultNum = Integer.parseInt(result);
		model.addAttribute("result", resultNum);

		return nextPage;
	}
	
	@GetMapping("/mail_sender_form")
	public String mailSenderForm() {
		
		String nextPage = "admin/mail_sender_form";
		return nextPage;
	}
	
	
	@PostMapping("/transfer_file_confirm")
	public String transferFile(AdminMailDto adminMailDto, Model model) {
		log.info("transferFileConfirm()");
				
		String result = null;
		MultipartFile chart_img = adminMailDto.getAm_chart_img();
		String savedfileName = uploadFileService.upload(chart_img);
		
		if(savedfileName != null) {
			log.info("chart_img upload success!");
			result = adminBoardService.sendChart(adminMailDto, savedfileName);
			if(result != null)
				log.info("chart_img send mail success!");
			
			else 
				log.info("chart_img send mail fail!");			
				
		} else {
				log.info("chart_img upload fail!");
		}
		return "admin/mail_sender_form";	
		
	}
}
