package com.maru.chaekmaru.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MypageService {
	
	@Autowired
	MypageDao mypageDao;
	
	public List<MemberCartDto> getMyCartList(String m_id) {
		log.info("getMyCartList");
		
		return mypageDao.getMyCartList(m_id);
		
	}

	public int addMyCart(String m_id) {
		log.info("addMyCart");
		
		int result = mypageDao.addMyCart(m_id);
		if (result < 0) {
			log.info("Add My Cart Fail");
			
		}
		
		return result;
		
	}

	public int deleteMyCart(String m_id) {
		log.info("deleteMyCartList");
		
		int result = mypageDao.deleteMyCart(m_id);
		if (result < 0) {
			log.info("Delete My Cart Fail");
			
		}
		
		return result;
		
	}

	

	

	

	
	
	

}
