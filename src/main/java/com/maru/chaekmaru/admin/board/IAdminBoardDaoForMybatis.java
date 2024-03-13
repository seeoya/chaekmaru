package com.maru.chaekmaru.admin.board;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAdminBoardDaoForMybatis {

	
	ArrayList<AdminBoardDto> selectDailySales();

	ArrayList<AdminBoardDto> selectDailySalesByCate();

	
	
	
//	public List<SaledBookDto> selectSaledBooksByPeriod(String saled_start, String saled_end);
//
//	public int countListResult();
//
//	public ArrayList<BookDto> setList(@Param("startNum") int startNum, @Param("endNum") int endNum);
		


	
}
