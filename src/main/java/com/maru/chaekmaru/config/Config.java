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
    final public static int LOGIN_FAIL = 118;
    final public static int PW_MODIFY_SAME_SUCCESS = 119;

	// BookController 100 ~ 199

	// MypageController 200 ~ 299
	final public static int MODIFY_CART_SUCCESS = 200;
	final public static int ADD_CART_SUCCESS = 201;
	final public static int DELETE_CART_SUCCESS = 202;
	final public static int PAYMENT_SUCCESS = 203;
	final public static int INSERT_POINT_SUCCESS = 204;
	final public static int DELETE_PAYMENT_CART_SUCCESS = 205; 
	final public static int POINT_CHARGE_SUCCESS = 206;
	final public static int ADD_PICK_SUCCESS = 207;
	final public static int CANCEL_PAYMENT_SUCCESS = 208;
	final public static int RETURN_PAYMENT_SUCCESS = 209;
	final public static int PAYMENT_CONFIRM_SUCCESS = 210;
	
	final public static int MODIFY_CART_FAIL = 240;
	final public static int ADD_CART_FAIL = 241;
	final public static int DELETE_CART_FAIL = 242;
	final public static int PAYMENT_FAIL = 243;
	final public static int INSERT_POINT_FAIL = 244;
	final public static int DELETE_PAYMENT_CART_FAIL = 245;
	final public static int POINT_CHARGE_FAIL = 246;
	final public static int ADD_PICK_FAIL = 247;
	final public static int ADD_PICK_DUPPLICATE = 248;
	final public static int MEMBER_BAN = 249;
	final public static int CANCEL_PAYMENT_FAIL = 250;
	final public static int RETURN_PAYMENT_FAIL = 251;
	final public static int PAYMENT_CONFIRM_FAIL = 252;
	
	// ReviewController 300 ~ 399
	final public static int REVIEW_WRITE_SUCCESS = 300;
	final public static int REVIEW_MODIFY_SUCCESS = 301;
	final public static int REVIEW_DELETE_SUCCESS = 302;
	
	final public static int REVIEW_WRITE_FAIL = 310;
	final public static int REVIEW_MODIFY_FAIL = 311; 
	final public static int REVIEW_DELETE_FAIL = 312;
	final public static int REVIEW_ALREADY_EXIST = 313;
	

	
	// AdminHomeController 400 ~ 499
	
	
	// AdminMemberController 500 ~ 599
	final public static int ADMIN_CREATE_ACCOUNT_SUCCESS = 500;  
	final public static int ADMIN_LOGIN_SUCCESS = 501;   	
	final public static int ADMIN_LOGOUT_SUCCESS = 502;         
	final public static int ADMIN_MEMBER_MODIFY_SUCCESS = 503; 
	final public static int ADMIN_MEMBER_DELETE_SUCCESS = 504;  
	final public static int SET_ADMIN_APPROVAL_SUCCESS_AT_DATABASE = 505;
	
	final public static int ADMIN_ID_ALREADY_EXIST = 510;    
	final public static int ADMIN_CREATE_ACCOUNT_FAIL = 511; 
	final public static int ADMIN_LOGIN_FAIL = 512;  
	final public static int ADMIN_MEMBER_MODIFY_FAIL = 513;
	final public static int ADMIN_MEMBER_DELETE_FAIL = 514;  
	final public static int SET_ADMIN_APPROVAL_FAIL_AT_DATABASE = 515;	
	
	// AdminBookController 600 ~ 699
	final public static int REGIST_BOOK_SUCCESS_AT_DATABASE = 600;	 
	final public static int MODIFY_BOOK_SUCCESS_AT_DATABASE = 601;	
	final public static int DELETE_BOOK_SUCCESS_AT_DATABASE = 602;
	
	final public static int BOOK_ALREADY_EXIST = 610;	          	
	final public static int REGIST_BOOK_FAIL_AT_DATABASE = 611; 
	final public static int MODIFY_BOOK_FAIL_AT_DATABASE = 612; 
	final public static int DELETE_BOOK_FAIL_AT_DATABASE = 613;
	final public static int DELETE_REVIEW_FAIL_AT_DATABASE = 614;
	

	// AdminShopController 700 ~ 799
	final public static int USER_ACCOUNT_UNACTIVE_SUCCESS = 700;		
	final public static int MODIFY_POINT_SUCCESS_AT_DATABASE = 701;	
	final public static int RETURN_BOOK_APPROVAL_SUCCESS = 702;
	final public static int RETURN_BOOK_NOT_APPROVED_SUCCESS = 703;
	final public static int MODIFY_BOOK_INVENTORY_SUCCESS = 704;
	
	final public static int USER_ACCOUNT_UNACTIVE_FAIL = 710;
	final public static int MODIFY_POINT_FAIL_AT_DATABASE = 711;	
	final public static int RETURN_BOOK_APPROVAL_FAIL = 712;
	final public static int MODIFY_BOOK_COUNT_FAIL = 713;
	final public static int RETURN_BOOK_NOT_APPROVED_FAIL = 714;    
    final public static int MODIFY_BOOK_INVENTORY_FAIL = 715;
    
    // AdminQNAController 800 ~ 899    
    final public static int QNA_REGIST_SUCCESS = 800;
    final public static int QNA_ANSWER_REGIST_SUCCESS = 801;
    final public static int QNA_DELETE_SUCCESS = 802;
    
    final public static int QNA_REGIST_FAIL = 810;
    final public static int QNA_ANSWER_REGIST_FAIL = 811;
    final public static int QNA_DELETE_FAIL = 812;
}