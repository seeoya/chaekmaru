package com.maru.chaekmaru.admin.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.config.Config;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminBookService {
	
	@Autowired
	IAdminBookDaoForMybatis adminBookDao;
	
	public int registBookConfirm(BookDto bookDto) {
		log.info("registBookConfirm()");
		
		boolean isBook = adminBookDao.isBook(bookDto.getB_isbn());
		
		if(!isBook) {
			int result = adminBookDao.insertBook(bookDto);
			
			if(result > 0)
				return Config.REGIST_BOOK_SUCCESS_AT_DATABASE;
			else 
				return Config.REGIST_BOOK_FAIL_AT_DATABASE;
			
		} else {
			return Config.BOOK_ALREADY_EXIST;
		}
			
		
	}

	public List<BookDto> bookListForm() {
		log.info("bookListForm()");
				
		return adminBookDao.selectAllBooks();
	}

	public BookDto modifyBookForm(int b_no) {
		log.info("modifyBookForm()");
		
		return adminBookDao.selectBookForModify(b_no);
		
		
	}

	public int modifyBookConfirm(BookDto bookDto) {
		log.info("modifyBookConfirm()");
		
		int result = adminBookDao.updateBookForModify(bookDto);
		if(result > 0)
			return Config.MODIFY_BOOK_SUCCESS_AT_DATABASE;
		else 
			return Config.MODIFY_BOOK_FAIL_AT_DATABASE;
		
		
	}

	public void deleteBookConfirm(int b_no) {
		log.info("deleteBookConfirm()");
		
		int result = adminBookDao.deleteBookConfirm(b_no);
		
		if(result > 0)
			log.info(Config.DELETE_SUCCESS_AT_DATABASE);
		else 
			log.info(Config.DELETE_FAIL_AT_DATABASE);		
	}

/*	public List<BookDto> adminSearchBookConfirm(String search) {
		log.info("adminSearchBookConfirm()");
		
		return adminBookDao.selectBookForSearch(search);
		
	}*/
	

}
