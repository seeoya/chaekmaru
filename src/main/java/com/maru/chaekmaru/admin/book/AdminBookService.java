package com.maru.chaekmaru.admin.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.shop.SaledBookDto;

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

	public List<SaledBookDto> bookListForm() {
		log.info("bookListForm()");
				
		return adminBookDao.selectAllBooks();;
	}
	

}
