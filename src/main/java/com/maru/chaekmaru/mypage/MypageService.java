package com.maru.chaekmaru.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.shop.SaledBookDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MypageService {
	
//	@Autowired
//	MypageDao mypageDao;
	
	@Autowired
	IMypageDaoForMybatis mypageDao;
	
	public List<MemberCartDto> getMyCartList(String m_id) {
		log.info("getMyCartList");
		
		return mypageDao.getMyCartList(m_id);
		
	}

	public int addBookCount(String m_id, int c_no, int c_book_count) {
		log.info("addBookCount");
		
		int result = mypageDao.addBookCount(m_id, c_no, c_book_count);

		if (result < 0) {
			log.info("Add My Cart Fail");
		}
		
		return result;
		
	}

	public int deleteMyCart(String m_id, int c_no) {
		log.info("deleteMyCartList");
		
		int result = mypageDao.deleteMyCart(m_id, c_no);
		
		return result;
		
	}

	public List<MemberCartDto> paymentForm(String m_id, int c_no) {
		log.info("paymentForm");
		
		return mypageDao.paymentForm(m_id, c_no);
		
	}

	public int paymentMyCart(SaledBookDto saledBookDto, String m_id) {
		log.info("paymentMyCart()");
		
		int result = mypageDao.paymentMyCart(saledBookDto, m_id);
		
		if (result > 0) {
//			result = mypageDao.deleteMyCart(m_id);
			
		} else {
			result = 0;
			
		}
		
		return result;
		
	}

	public int insertPoint(MyPointListDto myPointListDto) {
		log.info("insertPoint()");
		
		myPointListDto.setPl_payment_book_point(myPointListDto.getPl_payment_book_point() * -1);
		
		int result = mypageDao.insertPoint(myPointListDto);
		
		return result;
		
	}

	public int addMyCart(String m_id, int b_no) {
		log.info("addMyCart()");
		
		int result = mypageDao.addMyCart(m_id, b_no);
		
		return result;
		
	}

	

	

	

	

	
	
	

}
