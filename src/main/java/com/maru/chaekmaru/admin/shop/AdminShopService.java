package com.maru.chaekmaru.admin.shop;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.member.MemberDto;
import com.maru.chaekmaru.mypage.MyPointListDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminShopService {
	
	@Autowired
	IAdminShopDaoForMybatis adminShopDao;

	public List<MyPointListDto> pointListForm() {
		log.info("pointListForm()");
		
		return adminShopDao.selectAllUserPointsForSum();
		
	}
	
		
	public MyPointListDto pointManagementForm(String m_id) {
		log.info("pointManagementForm()");
		
		return adminShopDao.selectUserPointForModify(m_id);
	}

	public void modifyPointConfirm(MyPointListDto myPointListDto) {
		log.info("modifyPointConfirm()");
		
		int result = adminShopDao.insertPointForMyPointList(myPointListDto);
		if(result > 0) 
			log.info(Config.MODIFY_POINT_SUCCESS_AT_DATABASE);			
		 else
			log.info(Config.MODIFY_POINT_FAIL_AT_DATABASE);
	}

	public List<MyPointListDto> pointHistoryForm(String m_id) {
		log.info("pointHistoryForm()");
		
		return adminShopDao.selectUserPointForHistory(m_id);
	}


	public List<MemberDto> userAccountActiveForm() {
		log.info("userAccountActiveForm()");
		
		return adminShopDao.selectAllUsers();
		
	}


	public int userAccountActiveConfirm(String m_id) {
		log.info("userAccountActiveConfirm()");
		
		int result = adminShopDao.updaterActiveForUserState(m_id);
		if(result > 0) 
			return Config.MODIFY_POINT_SUCCESS_AT_DATABASE;			
		 else
			return Config.MODIFY_POINT_FAIL_AT_DATABASE;
		
	}

	

	
		
	

	
	

}
