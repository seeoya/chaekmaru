package com.maru.chaekmaru.admin.board;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.maru.chaekmaru.admin.member.AdminMemberDto;

@Mapper
public interface IAdminBoardDaoForMybatis {

	
	ArrayList<AdminBoardDto> selectDailySales();

	ArrayList<AdminBoardDto> selectMonthSalesByCate();

	ArrayList<AdminBoardDto> selectMonthSalesByCateThis();

	ArrayList<AdminMemberDto> selectMailsByAdmin();


	
}
