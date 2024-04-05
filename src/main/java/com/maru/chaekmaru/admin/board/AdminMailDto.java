package com.maru.chaekmaru.admin.board;

import org.springframework.web.multipart.MultipartFile;

import com.maru.chaekmaru.admin.member.AdminMemberDto;

import lombok.Data;

@Data
public class AdminMailDto {
	
	private String am_title;
	private String am_content;	
	private MultipartFile am_chart_img;
	private String am_from;
	
	private AdminMemberDto adminMemberDto;
}
