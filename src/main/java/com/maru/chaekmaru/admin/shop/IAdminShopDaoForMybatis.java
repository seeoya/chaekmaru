package com.maru.chaekmaru.admin.shop;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.maru.chaekmaru.member.MemberDto;
import com.maru.chaekmaru.mypage.MyPointListDto;

@Mapper
public interface IAdminShopDaoForMybatis {

	public List<MyPointListDto> selectAllUserPointsForSum();
	
	public MyPointListDto selectUserPointForModify(String m_id);
	
	public int insertPointForMyPointList(MyPointListDto myPointListDto);

	public List<MyPointListDto> selectUserPointForHistory(String m_id);

	public List<MemberDto> selectAllUsers();

	public int updaterActiveForUserState(String m_id);

	
	

}
