package com.maru.chaekmaru.member;

import lombok.Data;

@Data
public class MemberDto {
	
	private String m_id;
	private String m_pw;
	private String m_name;
	private String m_mail;
	private String m_phone;
	private String m_addr_code;
	private String m_addr;
	private String m_detail_addr;
	private int m_grade;
	private int m_state;
	private String m_reg_date;
	private String m_mod_date;

	private int point;
	
}
