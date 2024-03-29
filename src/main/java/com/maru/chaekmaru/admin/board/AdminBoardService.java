package com.maru.chaekmaru.admin.board;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminBoardService {

	@Autowired
	IAdminBoardDaoForMybatis adminBoardDao;

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

	
}
