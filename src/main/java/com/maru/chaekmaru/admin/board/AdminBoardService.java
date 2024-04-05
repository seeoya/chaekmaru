package com.maru.chaekmaru.admin.board;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maru.chaekmaru.admin.member.AdminMemberDto;

import jakarta.activation.FileDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminBoardService {

	@Autowired
	IAdminBoardDaoForMybatis adminBoardDao;
	
	@Autowired
	JavaMailSender javaMailSender;

	public ArrayList<AdminBoardDto> totalSalesDaily() {
		log.info("totalSalesDaily()");
		
		return adminBoardDao.selectDailySales();
	}

	public ArrayList<AdminBoardDto> salesCateMonth() {
		log.info("salesCateMonth()");
		
		return adminBoardDao.selectMonthSalesByCate();
		
	}
	
	public ArrayList<AdminBoardDto> salesCateMonthThis() {
		log.info("salesCateMonthThis()");
		
		return adminBoardDao.selectMonthSalesByCateThis();
		
	}

		
	public String sendChart(AdminMailDto adminMailDto, String fileName) {
		log.info("sendChart()");
		
		ArrayList<AdminMemberDto> mailsDto = adminBoardDao.selectMailsByAdmin();
		
		String result = null;
		
		if(mailsDto.size() > 0) {

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			try {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setSubject(adminMailDto.getAm_title());
				helper.setFrom(adminMailDto.getAm_from());
				String text = adminMailDto.getAm_content();
				String htmlContent = "<p style='color: #333; text-align: center; margin:0 auto;'>전달사항: " + text + "</p>" +
								 "<div style='width: 1000px; margin: 20px auto; padding: 20px 30px;'>" +				        
				        		 "<h2 style='color: #333; text-align: center; margin: 0 0 20px;'>" +
				        		 "책마루 매출 BOARD</h2>" +
				        		 "<img src='cid:image' alt='chart_img' style='display: block; margin: 0 auto;'>" +
				            	 "</div>";
				helper.setText(htmlContent, true);				
				MultipartFile file = adminMailDto.getAm_chart_img();
				helper.addInline("image", new ClassPathResource("static/img/chart/" + fileName));
				
				
				for(int i=0; i < mailsDto.size(); i++) {
					String email = mailsDto.get(i).getA_mail();
					log.info("email: {}", email);
					helper.setTo(email);		
					
					javaMailSender.send(mimeMessage);
					
					result = "success";
				}
				
			} catch (MessagingException e) {
				e.printStackTrace();
				log.error("이메일 전송 중 에러 발생", e);
		 
			}
			
		}
		return result;
		

	}
		

}
