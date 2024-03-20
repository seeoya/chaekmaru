package com.maru.chaekmaru.mypage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

	public int addMyCart(String m_id, int b_no, int count) {
		log.info("addMyCart()");
		int result = -1;

		int countBookInCart = -1;

		countBookInCart = mypageDao.selectBookCount(m_id, b_no);

		if (countBookInCart > 0) {
			result = mypageDao.addBookCountByBNo(m_id, b_no, count);
		} else {
			result = mypageDao.addMyCart(m_id, b_no, count);
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

	public int allPaymentMyCartList(String m_id, SaledBookDto saledBookDto, ArrayList<MemberCartDto> buyBooksDatas,
			int m_grade, int allPrice, int discount, int finalPrice) {
		log.info("allPaymentMyCartList()");

		int result = -1;
		int orderNo = 1;

		MyPointListDto myPointListDto = new MyPointListDto();

		int sbOrderNoCount = mypageDao.sbOrderNoCount(m_id);
		if (sbOrderNoCount == 0) {
			saledBookDto.setSb_order_no(orderNo);
		} else {
			orderNo = mypageDao.selectMaxSbOrderNo(m_id) + 1;
		}

		for (int i = 0; i < buyBooksDatas.size(); i++) {
			saledBookDto.setSb_order_no(orderNo);
			saledBookDto.setB_no(buyBooksDatas.get(i).getB_no());
			saledBookDto.setSb_book_count(buyBooksDatas.get(i).getC_book_count());
			saledBookDto.setB_name(buyBooksDatas.get(i).getB_name());
			int eachAllPrice = buyBooksDatas.get(i).getB_price() * buyBooksDatas.get(i).getC_book_count();
			log.info("eachAllPrice ====================> " + eachAllPrice);
			double dcPrice = 0;
			switch (m_grade) {
			case 0:
				log.info("m_grade======================>" + m_grade);
				dcPrice = eachAllPrice * 0;
				break;
			case 1:
				log.info("m_grade======================>" + m_grade);
				dcPrice = eachAllPrice * 0.05;
				break;
			case 2:
				log.info("m_grade======================>" + m_grade);
				dcPrice = eachAllPrice * 0.1;
				break;
			default:
				dcPrice = 0;
				break;
			}

			int intDiscount = (int) Math.ceil(dcPrice);
			saledBookDto.setSb_saled_price(intDiscount);
			log.info("intDiscount ====================> " + intDiscount);
			saledBookDto.setSb_all_price(eachAllPrice);
			int remainBooks = buyBooksDatas.get(i).getB_count() - buyBooksDatas.get(i).getC_book_count();
			int isOrderde = mypageDao.allPaymentMyCartList(m_id, saledBookDto);
			if (isOrderde < 0) {
				result = Config.DELETE_PAYMENT_CART_FAIL;
			} else {
				int minusBookCount = mypageDao.remainBooks(buyBooksDatas.get(i).getB_no(), remainBooks);

				if (minusBookCount < 0) {
					result = Config.MODIFY_BOOK_COUNT_FAIL;
				} else {
					int removeCart = mypageDao.removeCartByBNo(m_id, buyBooksDatas.get(i).getB_no());
					if (removeCart < 0) {
						result = Config.DELETE_CART_FAIL;
					}
				}
			}
		}

		myPointListDto.setPl_payment_book_point(-finalPrice);
		int allBookCount = mypageDao.sumAllBook(m_id, orderNo);
		myPointListDto.setPl_desc("도서 " + allBookCount + "권 구매");
		int removePointByBuyBooks = mypageDao.removePointByBuyBooks(m_id, myPointListDto);
		if (removePointByBuyBooks > 0) {
			result = Config.DELETE_PAYMENT_CART_SUCCESS;
		} else {
			result = Config.DELETE_PAYMENT_CART_FAIL;
		}

		if (result == Config.DELETE_PAYMENT_CART_SUCCESS) {
			gradeUpdateCheck(m_id);

			return Config.PAYMENT_SUCCESS;
		} else {
			return Config.PAYMENT_FAIL;
		}

	}

	public int gradeUpdateCheck(String m_id) {
		int allBuyPoint = sumSbAllPointByMId(m_id);
		int allSalePoint = sumSbSalePointByMId(m_id);
		int allBuyPointMId = allBuyPoint - allSalePoint;

		int grade = memberDao.getMemberGrade(m_id);
		int result = -1;

		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 등급:" + grade);
		
		if (allBuyPointMId >= 500000 && grade != 2) {
			result = updateGrade(m_id, 2);

			if (result > 0) {

				log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 등급:" + 2);
				return 2;
			}
		} else if (allBuyPointMId >= 100000 && grade != 1) {
			result = updateGrade(m_id, 1);

			if (result > 0) {

				log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 등급:" + 1);
				return 1;
			}
		} else if (grade != 0) {
			result = updateGrade(m_id, 0);

			if (result > 0) {

				log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 등급:" + 0);
				return 0;
			}
		}

		return grade;
	}

	public ArrayList<MemberCartDto> setPaymentForm(ArrayList<MemberCartDto> buyBooks) {

		ArrayList<MemberCartDto> buyBooksData = new ArrayList<>();
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

	public BookDto setView(int b_no) {
		return mypageDao.selectBook(b_no);
	}

	public List<SaledBookDto> getPaymentList(String m_id) {
		log.info("getPaymentList");

		return mypageDao.getPaymentList(m_id);

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
			return Config.ADD_PICK_DUPPLICATE;
		} else {
			result = mypageDao.addMyPick(m_id, b_no);
		}
		// #TODO RESULT 처리 필요
		if (result < 0) {
			log.info("찜 목록 등록에 실패 했습니다.");
			return Config.ADD_PICK_FAIL;
		} else {
			log.info("찜 목록 등록에 성공 했습니다.");
			return Config.ADD_PICK_SUCCESS;
		}
	}

	public List<MemberPickDto> myPickList(String m_id) {
		log.info("myPickList");

		return mypageDao.myPickList(m_id);
	}

	public MemberPickDto isMemberPick(String m_id, int b_no) {
		log.info("isMemberPick");

		log.info(m_id + "/" + b_no);

		return mypageDao.isMemberPick(m_id, b_no);
	}

	public int cancelMyPaymentList(String m_id, int sb_order_no) {
		int result = -1;
		MyPointListDto myPointListDto = new MyPointListDto();
		List<SaledBookDto> saledBookDtos = mypageDao.selectMyPaymentBySbON(sb_order_no, m_id);

		// 재고
		for (int i = 0; i < saledBookDtos.size(); i++) {
			int selectBNo = saledBookDtos.get(i).getB_no();
			log.info("saledBookDtos.get(i).getB_no() ==================>" + saledBookDtos.get(i).getB_no());
			int selectBookCountBySbNo = saledBookDtos.get(i).getSb_book_count();
			log.info("saledBookDtos.get(i).getSb_book_count() ==================>"
					+ saledBookDtos.get(i).getSb_book_count());
			int selectBookCountByBNo = saledBookDtos.get(i).getB_count();
			log.info("saledBookDtos.get(i).getB_count() ==================>" + saledBookDtos.get(i).getB_count());
			int updateCancelBookCount = selectBookCountBySbNo + selectBookCountByBNo;
			result = mypageDao.updateCancelBookCount(updateCancelBookCount, selectBNo);
		}

		if (result > 0) {
			// 금액
			int paymentPoint = mypageDao.paymentPoint(m_id, sb_order_no); // 결제 총액
			log.info("paymentPoint ===============>" + paymentPoint);
			int selectSalePrice = mypageDao.selectSalePrice(m_id, sb_order_no); // 할인 금액
			log.info("selectSalePrice ===============------->" + selectSalePrice);
			int returnPoint = (paymentPoint - selectSalePrice + 3000); // 반환 금액
			log.info("returnPoint ----------===============>" + returnPoint);
			int sumPaymentBookBySbON = mypageDao.sumPaymentBookBySbON(m_id, sb_order_no);
			log.info("sumPaymentBookBySbON +-+-+-+-+--+++-+-+-+-++->" + sumPaymentBookBySbON);
			myPointListDto.setPl_desc("도서 " + sumPaymentBookBySbON + "권 취소"); // 결제 도서 총합 (따로 구해야함)
			result = mypageDao.insertReturnPoint(myPointListDto, returnPoint, m_id);
			if (result > 0) {
				// state 1 -> 0 변경
				result = mypageDao.saledStateUpdateZero(m_id, sb_order_no); // 스테이트 변경
				log.info(result);
				gradeUpdateCheck(m_id);
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

	public int returnRequestBookPayment(String m_id, int sb_no, int b_no) {
		int result = mypageDao.saledStateUpdateThree(m_id, sb_no, b_no);

		return result;
	}

	public List<SaledBookDto> getOrderNo(String m_id) {

		return mypageDao.getOrderNo(m_id);
	}

	public LinkedHashMap<Integer, ArrayList<SaledBookDto>> getMyPaymentList(String m_id) {

		LinkedHashMap<Integer, ArrayList<SaledBookDto>> list = new LinkedHashMap<>();

		List<SaledBookDto> orderNos = getOrderNo(m_id);

		for (int i = 0; i < orderNos.size(); i++) {
			int o_no = orderNos.get(i).getSb_order_no();

			ArrayList<SaledBookDto> sBookDtos = mypageDao.getPaymentListByONo(m_id, o_no);

			list.put(o_no, sBookDtos);
		}

		return list;
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

	public int eachDiscount(int eachPrice, int m_grade) {
		double discount = 0;

		switch (m_grade) {
		case 0:
			log.info(m_grade);
			discount = eachPrice * 0;
			break;
		case 1:
			log.info(m_grade);
			discount = eachPrice * 0.05;
			break;
		case 2:
			log.info(m_grade);
			discount = eachPrice * 0.1;
			break;
		default:
			discount = 0;
			break;
		}

		return ((int) discount / 10) * 10;
	}

	public int sumSbAllPointByMId(String m_id) {

		return mypageDao.sumSbAllPointByMId(m_id);
	}

	public int sumSbSalePointByMId(String m_id) {

		return mypageDao.sumSbSalePointByMId(m_id);
	}

	public int updateGrade(String m_id, int state) {
		return mypageDao.updateGrade(m_id, state);
	}

	public LinkedHashMap<Integer, Integer> getMyPaymentStateList(LinkedHashMap<Integer, ArrayList<SaledBookDto>> list) {

		LinkedHashMap<Integer, Integer> stateList = new LinkedHashMap<>();

		List<Integer> keySet = new ArrayList<>(list.keySet());

		for (int i = 0; i < list.size(); i++) {
//				list의 i번째 key
			int key = keySet.get(i);

			int state = 1;
			// key = o_no
			// o_no의 현재 상태
			// 0 => 주문 취소, 1 => 주문 완료, 2 => 배송 완료
			// 3 => 반품 처리 중 , 4 => 반품 완료

			ArrayList<SaledBookDto> saledBookDtos = list.get(key);

			for (int j = 0; j < saledBookDtos.size(); j++) {
				if (saledBookDtos.get(j).getSb_state() == 0) {
					state = 0;
					break;
				}

				if (saledBookDtos.get(j).getSb_state() > 1) {
					state = 2;
					break;
				}
			}

			stateList.put(key, state);
		}

		return stateList;
	}

	public int confirmPayment(String m_id, int sb_order_no) {
		int result = -1;

		result = mypageDao.confirmPayment(m_id, sb_order_no);

		return result;
	}

	public LinkedHashMap<Integer, Integer> getMyAllPriceList(LinkedHashMap<Integer, ArrayList<SaledBookDto>> list) {

		LinkedHashMap<Integer, Integer> allPriceByKey = new LinkedHashMap<>();

		List<Integer> keySet = new ArrayList<>(list.keySet());

		for (int i = 0; i < list.size(); i++) {
//				list의 i번째 key
			int key = keySet.get(i);
			int getMyAllPriceList = 0;
			ArrayList<SaledBookDto> saledBookDtos = list.get(key);

			for (int j = 0; j < saledBookDtos.size(); j++) {
				getMyAllPriceList += saledBookDtos.get(j).getSb_all_price();
			}

			allPriceByKey.put(key, getMyAllPriceList);
		}

		return allPriceByKey;
	}

	public LinkedHashMap<Integer, Integer> saledPriceByKey(LinkedHashMap<Integer, ArrayList<SaledBookDto>> list) {

		LinkedHashMap<Integer, Integer> saledPriceByKey = new LinkedHashMap<>();

		List<Integer> keySet = new ArrayList<>(list.keySet());

		for (int i = 0; i < list.size(); i++) {
//				list의 i번째 key
			int key = keySet.get(i);
			int getSaledPriceList = 0;
			ArrayList<SaledBookDto> saledBookDtos = list.get(key);

			for (int j = 0; j < saledBookDtos.size(); j++) {
				getSaledPriceList += saledBookDtos.get(j).getSb_saled_price();
			}

			saledPriceByKey.put(key, getSaledPriceList);
		}

		return saledPriceByKey;
	}

	public int getState(String m_id) {

		int result = -1;
		
		result = memberDao.selectMState(m_id);

		return result;
	}
}