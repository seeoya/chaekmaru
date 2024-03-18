package com.maru.chaekmaru.admin.board;

import lombok.Data;

@Data
public class AdminBoardDto {
	
		
	private int sc_sales1;  // 7일전 매출액
	private int sc_sales2;  // 6일전 매출액 
	private int sc_sales3;  // 5일전 매출액
	private int sc_sales4;  // 4일전 매출액
	private int sc_sales5;  // 3일전 매출액
	private int sc_sales6;  // 2일전 매출액
	private int sc_sales7;  // 1일전 매출액
	
	private String sc_date1;  // 7일전
	private String sc_date2;  // 6일전
	private String sc_date3;  // 5일전
	private String sc_date4;  // 4일전	
	private String sc_date5;  // 3일전
	private String sc_date6;  // 2일전
	private String sc_date7;  // 1일전

	
	private int sc_c_sales0;  //카테고리0 전월 매출액
	private int sc_c_sales1;  //카테고리1 전월 매출액
	private int sc_c_sales2;  //카테고리2 전월 매출액
	private int sc_c_sales3;  //카테고리3 전월 매출액
	private int sc_c_sales4;  //카테고리4 전월 매출액
	private int sc_c_sales5;  //카테고리5 전월 매출액
	private int sc_c_sales6;  //카테고리6 전월 매출액
	private int sc_c_sales7;  //카테고리7 전월 매출액
	private int sc_c_sales8;  //카테고리8 전월 매출액
	private int sc_c_sales9;  //카테고리9 전월 매출액
		
	private String sc_c_date1;   //전월
	
	private int sc_c_sales10;  //카테고리0 당월 매출액
	private int sc_c_sales11;  //카테고리1 당월 매출액
	private int sc_c_sales12;  //카테고리2 당월 매출액
	private int sc_c_sales13;  //카테고리3 당월 매출액
	private int sc_c_sales14;  //카테고리4 당월 매출액
	private int sc_c_sales15;  //카테고리5 당월 매출액
	private int sc_c_sales16;  //카테고리6 당월 매출액
	private int sc_c_sales17;  //카테고리7 당월 매출액
	private int sc_c_sales18;  //카테고리8 당월 매출액
	private int sc_c_sales19;  //카테고리9 당월 매출액
	
	private String sc_c_date2;   //당월
	                 
	
}
