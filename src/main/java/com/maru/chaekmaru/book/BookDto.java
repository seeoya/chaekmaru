package com.maru.chaekmaru.book;

import lombok.Data;

@Data
public class BookDto {

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
	
	private double r_avg;
	private int r_count;
}
