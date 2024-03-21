package com.maru.chaekmaru.admin.shop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.book.ListPageDto;
import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.member.MemberDto;
import com.maru.chaekmaru.mypage.MyPointListDto;
import com.maru.chaekmaru.mypage.MypageService;
import com.maru.chaekmaru.shop.SaledBookDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminShopService {
	
	@Autowired
	IAdminShopDaoForMybatis adminShopDao;
	
	@Autowired
	MypageService mypageService;

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
		
		int result = adminShopDao.updateActiveForUserState(m_id);
		
		if(result > 0) 
			return Config.USER_ACCOUNT_UNACTIVE_SUCCESS;			
		 else
			return Config.USER_ACCOUNT_UNACTIVE_FAIL;
		
	}


	public List<SaledBookDto> saledBookListForm() {
		log.info("saledBookListForm()");
		
		return adminShopDao.selectAllSaledBooks();
		
	}
	
	public List<AdminShopDto> saledBookOrderCnt() {
		log.info("saledBookListForm()");
		
		return adminShopDao.selectOrderBookCNTs();
	}


	public SaledBookDto saledDetailForm(int sb_no) {
		log.info("saledDetailForm()");
		
		return adminShopDao.selectSaledBookForDetail(sb_no);
	}


	public List<SaledBookDto> returnBookListForm() {
		log.info("returnBookListForm()");
		
		return adminShopDao.selectAllReturnBooks();
		
	}
	
	public List<SaledBookDto> returnBookHistoryList() {
		log.info("returnBookHistoryList()");
		
		return adminShopDao.selectAllReturnBooksForHistory();
	}


	
	public SaledBookDto returnBookDetailForm(int sb_no) {
		log.info("returnBookDetailForm()");
		
		return adminShopDao.selectReturnBookForDetail(sb_no);
	}

	public void returnApprovalConfirm(int sb_no, int b_no, int sb_book_count) {
		log.info("returnApprovalConfirm()");
				
		int result = adminShopDao.updateApprovalForReturnBook(sb_no);
		
		if(result > 0) {
			
			result = adminShopDao.updateBookCount(b_no, sb_book_count);
					
			if(result > 0)
		
				log.info(Config.RETURN_BOOK_APPROVAL_SUCCESS);
			else
				log.info(Config.MODIFY_BOOK_COUNT_FAIL);
		
		} else {
			
			log.info(Config.RETURN_BOOK_APPROVAL_FAIL);
		}
	}


	public void returnNotApprovedConfirm(int sb_no) {
		log.info("returnNotApprovedConfirm()");
		
		int result = adminShopDao.updateNotApprovedForReturnBook(sb_no);
		if(result > 0) 
			log.info(Config.RETURN_BOOK_NOT_APPROVED_SUCCESS);			
		else
			log.info(Config.RETURN_BOOK_NOT_APPROVED_FAIL);
	}

	public BookDto modifyBookInventoryForm(int b_no) {
		log.info("modifyBookInventoryForm()");
		
		return adminShopDao.selectBookForInventory(b_no);
			
	}


	public void modifyBookInventoryConfirm(BookDto bookDto) {
		log.info("returnNotApprovedConfirm()");
		
		int result = adminShopDao.updateBookInventory(bookDto);
		if(result > 0) 
			log.info(Config.MODIFY_BOOK_INVENTORY_SUCCESS);			
		else
			log.info(Config.MODIFY_BOOK_INVENTORY_FAIL);
		
	}


	public List<SaledBookDto> saledBooksByPeriod(String saled_start, String saled_end) {
		log.info("saledBooksByPeriod()");
		
		return adminShopDao.selectSaledBooksByPeriod(saled_start, saled_end);
	}


	public int countBook() {
		log.info("countBook()");
				
		int count = adminShopDao.countListResult();

		return count;
	}

	public int countAllPage(int count, int pageItemPerPage) {
		log.info("countAllPage()");
		
		return (int) Math.ceil((double) count / pageItemPerPage);
		
	}
	
	public ArrayList<BookDto> setList(int pageItemPerPage, int nowPage) {
		log.info("setList()");
		
		ArrayList<BookDto> bookDtos = new ArrayList<>();
		
		int startNum = (pageItemPerPage * (nowPage - 1)) + 1;
		int endNum = pageItemPerPage * nowPage;

		log.info(startNum + "+" + endNum);

		bookDtos = adminShopDao.setList(startNum, endNum);

		return bookDtos;
	}


	public ArrayList<ListPageDto> setPaging(int pageItemPerPage, int nowPageCount, int allPageCount) {
		log.info("setPaging()");
		
		ArrayList<ListPageDto> listPageDtos = new ArrayList<>();

		if (nowPageCount > 3) {
			listPageDtos.add(new ListPageDto("start", "start", 1));
		}

		if (nowPageCount - 3 > 0) {
			listPageDtos.add(new ListPageDto("prev", "prev", nowPageCount - 3));
		}

		if (nowPageCount - 4 > 0 && nowPageCount + 2 > allPageCount) {
			listPageDtos.add(new ListPageDto("num", "num", nowPageCount - 4));
		}

		if (nowPageCount - 3 > 0 && nowPageCount + 1 > allPageCount) {
			listPageDtos.add(new ListPageDto("num", "num", nowPageCount - 3));
		}

		if (nowPageCount - 2 > 0) {
			listPageDtos.add(new ListPageDto("num", "num", nowPageCount - 2));
		}

		if (nowPageCount - 1 > 0) {
			listPageDtos.add(new ListPageDto("num", "num", nowPageCount - 1));
		}

		listPageDtos.add(new ListPageDto("num", "num now", nowPageCount));

		if (nowPageCount + 1 <= allPageCount) {
			listPageDtos.add(new ListPageDto("num", "num", nowPageCount + 1));
		}

		if (nowPageCount + 2 <= allPageCount) {
			listPageDtos.add(new ListPageDto("num", "num", nowPageCount + 2));
		}

		if (nowPageCount + 3 <= allPageCount && nowPageCount - 1 <= 0) {
			listPageDtos.add(new ListPageDto("num", "num", nowPageCount + 3));
		}

		if (nowPageCount + 4 <= allPageCount && nowPageCount - 2 <= 0) {
			listPageDtos.add(new ListPageDto("num", "num", nowPageCount + 4));
		}

		if (nowPageCount + 3 <= allPageCount) {
			listPageDtos.add(new ListPageDto("next", "next", nowPageCount + 3));
		}

		if (nowPageCount <= allPageCount - 3) {
			listPageDtos.add(new ListPageDto("end", "end", allPageCount));
		}

		return listPageDtos;
	}


	public void retrunPoint(int sb_no) {
		log.info("retrunPoint()");
		
		MyPointListDto myPointListDto = new MyPointListDto();
		myPointListDto.setM_id(adminShopDao.getMId(sb_no));
		int selectSbAllPointBySbNo = adminShopDao.selectSbAllPointBySbNo(sb_no);
		
		int selectSalePointBySbNo = adminShopDao.selectSalePointBySbNo(sb_no);
		
		int returnPoint = selectSbAllPointBySbNo - selectSalePointBySbNo;
		
		myPointListDto.setPl_payment_book_point(returnPoint);
		myPointListDto.setPl_desc("도서 반품");
		
		int result = adminShopDao.insertRetrunPayment(myPointListDto);
		if(result > 0) {
			log.info(Config.INSERT_POINT_SUCCESS);
			mypageService.gradeUpdateCheck(adminShopDao.getMId(sb_no));
		} else
			log.info(Config.INSERT_POINT_FAIL);
		
	}


	
	
}
