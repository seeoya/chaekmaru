package com.maru.chaekmaru.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.book.BookDto;
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

		}

		return result;

	}

	public int insertPoint(MyPointListDto myPointListDto) {
		log.info("insertPoint()");

		myPointListDto.setPl_payment_book_point(myPointListDto.getPl_payment_book_point() * -1);

		int result = mypageDao.insertPoint(myPointListDto);

		return result;

	}

	public List<MemberCartDto> allPaymentForm(String m_id) {
		log.info("allPaymentForm()");

		return mypageDao.allPaymentForm(m_id);
	}

	public int addMyCart(String m_id, int b_no) {
		log.info("addMyCart()");
		int result = -1;

		int countBookInCart = -1;

		countBookInCart = mypageDao.selectBookCount(m_id, b_no);

		if (countBookInCart > 0) {
			result = mypageDao.addBookCountByBNo(m_id, b_no);
		} else {
			result = mypageDao.addMyCart(m_id, b_no);
		}

		return result;
	}

	public int calcAllPrice(List<MemberCartDto> memberCartDtos) {
		int allPrice = 0;

		for (int i = 0; i < memberCartDtos.size(); i++) {
			allPrice += memberCartDtos.get(i).getB_price() * memberCartDtos.get(i).getC_book_count();
		}

		return allPrice;
	}

	public int memberDiscount(int allPrice, int grade) {
		double discount = 0;

		switch (grade) {
		case 0:
			log.info(grade);
			discount = allPrice * 0;
			break;
		case 1:
			log.info(grade);
			discount = allPrice * 0.05;
			break;
		case 2:
			log.info(grade);
			discount = allPrice * 0.1;
			break;
		default:
			discount = 0;
			break;
		}

		return ((int) discount / 10) * 10;
	}

	public int allPaymentMyCartList(String m_id, SaledBookDto saledBookDto) {
		log.info("allPaymentMyCartList()");

		List<MemberCartDto> memberCartDtos = mypageDao.getMyCartList(m_id);

		int result = -1;

		for (int i = 0; i < memberCartDtos.size(); i++) {

			saledBookDto.setB_no(memberCartDtos.get(i).getB_no());
			saledBookDto.setSb_book_count(memberCartDtos.get(i).getC_book_count());
			saledBookDto.setB_name(memberCartDtos.get(i).getB_name());
			saledBookDto.setSb_all_price(memberCartDtos.get(i).getB_price() * memberCartDtos.get(i).getC_book_count());

			result = mypageDao.allPaymentMyCartList(m_id, saledBookDto);

		}

		return result;

	}

	public int insertAllPoint(MyPointListDto myPointListDto, String m_id) {
		log.info("insertAllPoint()");

		List<MemberCartDto> memberCartDtos = mypageDao.getMyCartList(m_id);

		int result = -1;

		for (int j = 0; j < memberCartDtos.size(); j++) {

			myPointListDto.setPl_payment_book_point(
					memberCartDtos.get(j).getB_price() * memberCartDtos.get(j).getC_book_count() * -1);
			myPointListDto.setPl_desc("도서 " + Integer.toString(memberCartDtos.get(j).getC_book_count()) + "권 구매");

		}

		result = mypageDao.insertPoint(myPointListDto);

		return result;

	}

	public int deleteAllMyCart(String m_id) {
		log.info("deleteAllMyCart()");

		int result = -1;

		result = mypageDao.deleteAllMyCart(m_id);

		return result;

	}

	public int deletePaymentMyCart(String m_id, int b_no) {
		log.info("deletePaymentMyCart()");
		int result = -1;

		result = mypageDao.deleteMyCartByBNo(m_id, b_no);

		return result;

	}

	public BookDto setView(int b_no) {
		return mypageDao.selectBook(b_no);
	}

	public List<SaledBookDto> getPaymentList(String m_id) {
		log.info("getPaymentList");

		return mypageDao.getPaymentList(m_id);

	}

}
