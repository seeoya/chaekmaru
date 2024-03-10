package com.maru.chaekmaru.admin.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.member.MemberDto;
import com.maru.chaekmaru.mypage.MyPointListDto;
import com.maru.chaekmaru.shop.SaledBookDto;

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


	public List<BookDto> bookInventoryListForm() {
		log.info("bookInventoryListForm()");
		
		return adminShopDao.selectAllBooksForInventory();
	
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


	

		

}
