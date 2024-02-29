package com.maru.chaekmaru.mypage;

import lombok.Data;

@Data
public class MemberPickDto {
	
	private int up_no;
	private String up_reg_date;
	
	private String m_id;
	private String m_pw;
	private String m_name;
	private String m_nickname;
	private String m_mail;
	private String m_phone;
	private String m_addr;
	private int m_grade;
	private int m_state;
	private String m_reg_date;
	private String m_mod_date;
	
	private int b_no;
	private String b_thumnail;
	private String b_name;
	private String b_author;
	private String b_intorduce;
	private String b_publisher;
	private String b_publish_date;
	private String b_kdc;
	private String b_isbn;
	private String b_price;
	private int b_count;
	private String b_reg_date;
	private String b_mod_date;

}
