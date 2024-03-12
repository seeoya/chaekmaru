package com.maru.chaekmaru.config;

public class Config {
	// COMMON
	final public static String LOGINED_MEMBER_INFO = "loginedMemberInfo";	
	
	// CONTROLLER LOG
	final public static int UNKNOWN_ERROR = -1;
	final public static int DATABASE_COMMUNICATION_TROUBLE = 2;
	
	
	
	// MainController
	
	// MemberController 100 ~ 199
	final public static int CREATE_ACCOUNT_SUCCESS = 100;
	final public static int MEMBER_MODIFY_SUCCESS = 101; 	
	final public static int LOGOUT_SUCCESS = 102; 
	final public static int MEMBER_DELETE_SUCCESS = 103;
	final public static int FIND_ID_SUCCESS = 104;
	final public static int FIND_PW_SUCCESS = 105;
	final public static int PW_MODIFY_SUCCESS = 106;
	final public static int LOGIN_SUCCESS = 107; 
	
	final public static int CREATE_ACCOUNT_FAIL = 110;
	final public static int ID_ALREADY_EXIST = 111;
	final public static int MEMBER_MODIFY_FAIL = 112;
	final public static int MEMBER_NOT_FOUND = 113;
	final public static int MEMBER_DELETE_FAIL = 114;
	final public static int FIND_ID_FAIL = 115;
	final public static int FIND_PW_FAIL = 116;
	final public static int PW_MODIFY_FAIL = 117;

	// BookController 100 ~ 199
		
	// MypageController 200 ~ 299
	final public static int MODIFY_CART_SUCCESS = 200;
	final public static int ADD_CART_SUCCESS = 201;
	final public static int DELETE_CART_SUCCESS = 202;
	final public static int PAYMENT_SUCCESS = 203;
	final public static int INSERT_POINT_SUCCESS = 204;
	final public static int DELETE_PAYMENT_CART_SUCCESS = 205; 
	final public static int POINT_CHARGE_SUCCESS = 206;
	
	final public static int MODIFY_CART_FAIL = 210;
	final public static int ADD_CART_FAIL = 211;
	final public static int DELETE_CART_FAIL = 212;
	final public static int PAYMENT_FAIL = 213;
	final public static int INSERT_POINT_FAIL = 214;
	final public static int DELETE_PAYMENT_CART_FAIL = 215;
	final public static int POINT_CHARGE_FAIL = 216;

	// ReviewController 300 ~ 399
	final public static int REVIEW_WRITE_SUCCESS = 300;
	final public static int REVIEW_MODIFY_SUCCESS = 301;
	final public static int REVIEW_DELETE_SUCCESS = 302;
	
	final public static int REVIEW_WRITE_FAIL = 310;
	final public static int REVIEW_MODIFY_FAIL = 311; 
	final public static int REVIEW_DELETE_FAIL = 312;

	// AdminHomeController 400 ~ 499
//	final public static int 
//	final public static int 
//	final public static int 
//	final public static int 
//	final public static int 
//	final public static int 
	
	// AdminMemberController 500 ~ 599
//	final public static int 
//	final public static int 
//	final public static int 
//	final public static int 
//	final public static int 
//	final public static int 
	
	// AdminBookController 600 ~ 699
//	final public static int 
//	final public static int 
//	final public static int 
//	final public static int 
//	final public static int 
//	final public static int 
	
	
	final public static int INSERT_SUCCESS_AT_DATABASE = 1;
//	final public static int ID_ALREADY_EXIST = 2;
//	final public static int DATABASE_COMMUNICATION_TROUBLE = 3;
 	final public static int INSERT_FAIL_AT_DATABASE = 4;
	
//	final public static int LOGIN_SUCCESS = 5;
	final public static int LOGIN_FAIL = 6;
	
	final public static int MODIFY_SUCCESS_AT_DATABASE = 7;
	final public static int MODIFY_FAIL_AT_DATABASE = 8;
	
	
	final public static int DELETE_SUCCESS_AT_DATABASE = 9;
	final public static int DELETE_FAIL_AT_DATABASE = 10;
	
	final public static int MEMBER_ACTIVE_DELETE_SUCCESS_AT_DATABASE = 11;
	final public static int MEMBER_ACTIVE_DELETE_FAIL_AT_DATABASE = 12;
	
	final public static int SET_ADMIN_APPROVAL_SUCCESS_AT_DATABASE = 13;
	final public static int SET_ADMIN_APPROVAL_FAIL_AT_DATABASE = 14;	
	
	final public static int REGIST_BOOK_SUCCESS_AT_DATABASE = 15;	
	final public static int BOOK_ALREADY_EXIST = 16;	
	final public static int REGIST_BOOK_FAIL_AT_DATABASE = 17;
	
	final public static int MODIFY_BOOK_SUCCESS_AT_DATABASE = 18;		
	final public static int MODIFY_BOOK_FAIL_AT_DATABASE = 19;
	
	final public static int MODIFY_POINT_SUCCESS_AT_DATABASE = 20;	
	final public static int MODIFY_POINT_FAIL_AT_DATABASE = 21;
	
	final public static int USER_ACCOUNT_UNACTIVE_SUCCESS = 22;
	final public static int USER_ACCOUNT_UNACTIVE_FAIL = 23;
	
	final public static int RETURN_BOOK_APPROVAL_SUCCESS = 24;
	final public static int RETURN_BOOK_APPROVAL_FAIL = 25;
	
	final public static int RETURN_BOOK_NOT_APPROVED_SUCCESS = 26;     
    final public static int RETURN_BOOK_NOT_APPROVED_FAIL = 27;
    final public static int MODIFY_BOOK_COUNT_FAIL = 28;
    
    final public static int MODIFY_BOOK_INVENTORY_SUCCESS = 29;
    final public static int MODIFY_BOOK_INVENTORY_FAIL = 30;

    final public static int DELETE_BOOK_SUCCESS_AT_DATABASE = 31;
    final public static int DELETE_BOOK_FAIL_AT_DATABASE = 32;
    
    

}