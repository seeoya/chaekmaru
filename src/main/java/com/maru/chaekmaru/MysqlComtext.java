package com.maru.chaekmaru;

import java.sql.Connection;
import java.sql.DriverManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MysqlComtext {
	
	
	public void conText() {
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
	         Connection conn = DriverManager.getConnection(
	               "jdbc:mysql://14.42.124.94:3306/DB_KIOSK?serverTimezone=Asia/Seoul", 
	               "root", 
	               "1234");
	         
	         log.info("conn: " + conn);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	

}
