package com.maru.chaekmaru.mypage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.member.IMemberDaoForMybatis;
import com.maru.chaekmaru.member.MemberDto;
import com.maru.chaekmaru.shop.SaledBookDto;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MypageService {

	@Autowired
	IMypageDaoForMybatis mypageDao;

	@Autowired
	IMemberDaoForMybatis memberDao;

	public List<MemberCartDto> getMyCartList(String m_id) {
		log.info("getMyCartList");

		log.info(m_id);

		return mypageDao.getMyCartList(m_id);
	}

	public int addBookCount(String m_id, int c_no, int c_book_count) {
		log.info("addBookCount");

		int result = mypageDao.addBookCount(m_id, c_no, c_book_count);

		if (result < 0) {
			result = Config.MODIFY_CART_FAIL;
		} else {
			result = Config.MODIFY_CART_SUCCESS;
		}

		return result;
	}

	public int deleteMyCart(String m_id, int c_no) {
		log.info("deleteMyCartList");

		int result = mypageDao.deleteMyCart(m_id, c_no);

		if (result < 0) {
			result = Config.DELETE_CART_FAIL;
		} else {
			result = Config.DELETE_CART_SUCCESS;
		}

		return result;
	}

	public List<MemberCartDto> paymentForm(String m_id, int b_no) {
		log.info("paymentForm");

		return mypageDao.paymentForm(m_id, b_no);

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

		if (result < 0) {
			result = Config.ADD_CART_FAIL;
		} else {
			result = Config.ADD_CART_SUCCESS;
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

	public int allPaymentMyCartList(String m_id, SaledBookDto saledBookDto, int b_no) {
		log.info("allPaymentMyCartList()");

		List<MemberCartDto> memberCartDtos = mypageDao.getMyCartList(m_id);
		int result = -1;
		int maxOrderNo = 0;
		int sbOrderNoCount = mypageDao.sbOrderNoCount(m_id);
		log.info("saledBookDto.getB_no" + saledBookDto.getB_no()); // 단 하나의 b_no 출력
		if (sbOrderNoCount == 0) {
			maxOrderNo = 1;
			saledBookDto.setSb_order_no(maxOrderNo);
			saledBookDto.setB_no(memberCartDtos.get(0).getB_no());
			saledBookDto.setSb_book_count(memberCartDtos.get(0).getC_book_count());
			saledBookDto.setB_name(memberCartDtos.get(0).getB_name());
			saledBookDto.setSb_all_price(memberCartDtos.get(0).getB_price() * memberCartDtos.get(0).getC_book_count());
			mypageDao.nowBooks(saledBookDto.getSb_book_count(), saledBookDto.getB_count(), saledBookDto.getB_no());
		} else {
			maxOrderNo = mypageDao.selectMaxSbOrderNo(m_id) + 1;
			for (int i = 0; i < memberCartDtos.size(); i++) {
				saledBookDto.setSb_order_no(maxOrderNo);
				saledBookDto.setB_no(memberCartDtos.get(i).getB_no());
				saledBookDto.setSb_book_count(memberCartDtos.get(i).getC_book_count());
				saledBookDto.setB_name(memberCartDtos.get(i).getB_name());
				saledBookDto
						.setSb_all_price(memberCartDtos.get(i).getB_price() * memberCartDtos.get(i).getC_book_count());
				mypageDao.nowBooks(saledBookDto.getSb_book_count(), saledBookDto.getB_count(), saledBookDto.getB_no());
				mypageDao.deleteMyCartByBNo(m_id, memberCartDtos.get(i).getB_no());
			}
		}

		result = mypageDao.allPaymentMyCartList(m_id, saledBookDto);

		// if 이 주문서가 장바구니에서 왔고, 내 장바구니에 b_no = 1 있으면 지워

		return result;
	}

	public ArrayList<MemberCartDto> setPaymentForm(ArrayList<MemberCartDto> buyBooks) {

		ArrayList<MemberCartDto> buyBooksData = new ArrayList<>();

//		MemberCartDto temp = mypageDao.selectBookData(buyBooks.get(0).getB_no());

//		buyBooksData.add(temp);

//		log.info("--------------------" + buyBooks.size());

		MemberCartDto temp = new MemberCartDto();

		for (int i = 0; i < buyBooks.size(); i++) {
			temp = new MemberCartDto();
			temp = mypageDao.selectBookData(buyBooks.get(i).getB_no());
			temp.setC_book_count(buyBooks.get(i).getC_book_count());

			buyBooksData.add(temp);
		}

		return buyBooksData;
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

	public int deleteAllMyCart(String m_id, int b_no) {
		log.info("deleteAllMyCart()");

		int result = -1;

		result = mypageDao.deleteAllMyCart(m_id, b_no);

		return result;

	}

//	public int deletePaymentMyCart(String m_id, int b_no) {
//		log.info("deletePaymentMyCart()");
//		int result = -1;
//
//		result = mypageDao.deleteMyCartByBNo(m_id, b_no);
//
//		return result;
//
//	}

	public BookDto setView(int b_no) {
		return mypageDao.selectBook(b_no);
	}

	public List<SaledBookDto> getPaymentList(String m_id) {
		log.info("getPaymentList");

		return mypageDao.getPaymentList(m_id);

	}

	public void nowBooks(int sb_book_count, int b_count, int b_no) {
		log.info("nowBooks");

		mypageDao.nowBooks(sb_book_count, b_count, b_no);
	}

	public int currentPoint(HttpSession session) {
		int point = -1;

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		if (loginedMemberDto != null) {
			point = memberDao.selectNowPoint(loginedMemberDto.getM_id());
			loginedMemberDto.setPoint(point);

		}

		return point;
	}

	public int addMyPick(String m_id, int b_no) {
		log.info("addMyPick()");
		int result = -1;

		int countBookInList = mypageDao.selectBookInMemberPick(m_id, b_no);
		log.info("countBookInList ==========================>" + countBookInList);
		log.info(m_id);
		log.info(b_no);
		// #TODO RESULT 처리 필요
		if (countBookInList > 0) {
			log.info("찜 목록에 이미 있는 도서 입니다.");
		} else {
			result = mypageDao.addMyPick(m_id, b_no);
		}
		// #TODO RESULT 처리 필요
		if (result < 0) {
			log.info("찜 목록 등록에 실패 했습니다.");
		} else {
			log.info("찜 목록 등록에 성공 했습니다.");
		}

		return result;
	}

	public List<MemberPickDto> myPickList(String m_id) {
		log.info("myPickList");

		return mypageDao.myPickList(m_id);
	}

	public int cancelMyPaymentList(String m_id, int sb_no, int b_no) {
		int result = -1;
		MyPointListDto myPointListDto = new MyPointListDto();

		// 재고
		int selectBookCountBySbNo = mypageDao.selectBookCountBySbNo(sb_no);
		int selectBookCountByBNo = mypageDao.selectBookCountByBNo(b_no);
		int updateCancelBookCount = selectBookCountBySbNo + selectBookCountByBNo;

		result = mypageDao.updateCancelBookCount(updateCancelBookCount, b_no);

		if (result > 0) {
			// 금액
			int paymentPoint = mypageDao.paymentPoint(m_id, sb_no, b_no);

			myPointListDto.setM_id(m_id);
			myPointListDto.setPl_payment_book_point(paymentPoint);
			myPointListDto.setPl_desc("도서 " + selectBookCountBySbNo + "권 취소");
			result = mypageDao.insertReturnPoint(myPointListDto, paymentPoint, m_id);
			if (result > 0) {
				// state 1 -> 0 변경
				result = mypageDao.saledStateUpdateZero(m_id, sb_no, b_no);
				log.info(result);
			}
		}

		return result;
	}

	public ArrayList<MyPointListDto> getPointList(String m_id) {
		ArrayList<MyPointListDto> myPointListDtos = new ArrayList<>();

		myPointListDtos = mypageDao.selectMyPointList(m_id);

		return myPointListDtos;
	}

	public int chargePoint(MyPointListDto myPointListDto) {
		int result = mypageDao.insertPoint(myPointListDto);

		if (result > 0) {
			return Config.POINT_CHARGE_SUCCESS;
		} else {
			return Config.POINT_CHARGE_FAIL;
		}
	}

	public int deleteMyPick(String m_id, int b_no) {
		log.info("deleteMyPick");

		int result = mypageDao.deleteMyPick(m_id, b_no);

		// #TODO RESULT 처리 필요
		if (result < 0) {
			result = Config.DELETE_CART_FAIL;
		} else {
			result = Config.DELETE_CART_SUCCESS;
		}
		// #TODO RESULT 처리 필요
		return result;
	}

	public List<MemberCartDto> directPayment(String m_id, int b_no) {

		return mypageDao.getBookDetail(b_no);
	}

	public int returnRequestBookPayment(String m_id, int sb_no, int b_no) {
		int result = mypageDao.saledStateUpdateThree(m_id, sb_no, b_no);

		return result;
	}

	public ArrayList<AttendenceDto> getAttendenceList(String m_id) {

		ArrayList<AttendenceDto> attendenceDtos = mypageDao.selectAttendenceList(m_id);

		int listSize = attendenceDtos.size();

		int startNum = (listSize / 10) * 10;

		// 오늘 출석했고, 리스트 사이즈가 10의 배수일 때 (한판 꽉 채웠을 때)
		if (checkTodayAttend(m_id) && listSize % 10 == 0) {
			startNum = ((listSize / 10) - 1) * 10;
		}

		if (listSize > startNum && listSize > 0) {
			attendenceDtos = new ArrayList<>(attendenceDtos.subList(startNum, listSize));

			for (int i = 0; i < attendenceDtos.size(); i++) {
				attendenceDtos.get(i).setAc_reg_date(attendenceDtos.get(i).getAc_reg_date().substring(0, 10));
			}

		} else {
			attendenceDtos = new ArrayList<>();
		}

		int leftNum = 10 - attendenceDtos.size();

		if (leftNum > 0) {
			for (int i = 0; i < leftNum; i++) {
				attendenceDtos.add(new AttendenceDto(""));
			}
		}

		return attendenceDtos;
	}

	public int attendence(String m_id) {

		int result = -1;

		int acc = mypageDao.selectAccAttendence(m_id) + 1;

		result = mypageDao.insertAttendence(m_id, acc);

		return result;
	}

	public boolean checkTodayAttend(String m_id) {
		ArrayList<AttendenceDto> attendenceDtos = mypageDao.selectAttendenceList(m_id);
		int listSize = attendenceDtos.size();

		String lastAttend = "";
		String today = "";

		if (listSize > 0 && attendenceDtos.get(listSize - 1).getAc_reg_date() != null && today != null) {
			lastAttend = attendenceDtos.get(listSize - 1).getAc_reg_date().substring(0, 10);
			today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

			if (today.equals(lastAttend)) {
				return true;
			}
		}

		return false;
	}

	public int attendenceAcc(String m_id) {
		ArrayList<AttendenceDto> attendenceDtos = mypageDao.selectAttendenceList(m_id);
		int listSize = attendenceDtos.size();

		if (listSize > 0) {
			return attendenceDtos.get(listSize - 1).getAc_attend_date();
		}

		return 0;
	}
}