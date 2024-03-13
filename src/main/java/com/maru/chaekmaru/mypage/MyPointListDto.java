package com.maru.chaekmaru.mypage;

import lombok.Data;

@Data
public class MyPointListDto {
	
	private int pl_payment_book_point;
	private String pl_reg_date;
	private String pl_desc;
	
	
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
	
	private int sb_no;
	private int sb_order_no;
	private int sb_book_count;
	private String sb_name;
	private String sb_addr_code;
	private String sb_addr;
	private String sb_detail_addr;
	private String sb_memo;
	private String sb_sale_date;
	private int sb_all_price;
	private int sb_saled_price;
	private int sb_state;
	private String sb_return_date;
	private String sb_reg_date;
	private String sb_mod_date;
	
	private int b_no;
	private String b_thumbnail;
	private String b_name;
	private String b_author;
	private String b_introduce;
	private String b_publisher;
	private String b_publish_date;
	private String b_kdc;
	private String b_isbn;
	private int b_price;
	private int b_count;
	private String b_reg_date;
	private String b_mod_date;
	
}
