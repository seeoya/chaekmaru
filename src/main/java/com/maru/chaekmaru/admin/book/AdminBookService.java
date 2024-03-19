package com.maru.chaekmaru.admin.book;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.book.ListPageDto;
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

	public int deleteBookConfirm(int b_no) {
		log.info("deleteBookConfirm()");
		
		int result = adminBookDao.deleteBookConfirm(b_no);
		
		if(result > 0) {
			result = adminBookDao.deleteReview(b_no);
			
			if(result > 0)		
				return Config.DELETE_BOOK_SUCCESS_AT_DATABASE;
			else
				return Config.DELETE_REVIEW_FAIL_AT_DATABASE;
		} else {	
				return Config.DELETE_BOOK_FAIL_AT_DATABASE;		
		}
	}

	public int countBook(String search, String sort) {
		log.info("countBook()");
		
		String sortSql = setSortSql(sort);
		int count = adminBookDao.countListResult(search, sortSql);

		return count;
	}

	public int countAllPage(int count, int pageItemPerPage) {
		log.info("countAllPage()");
		
		return (int) Math.ceil((double) count / pageItemPerPage);
		
	}

	public ArrayList<BookDto> setList(String search, String sort, int pageItemPerPage, int nowPage) {
		log.info("setList()");
		
		ArrayList<BookDto> bookDtos = new ArrayList<>();
		
		String sortSql = setSortSql(sort);

		int startNum = (pageItemPerPage * (nowPage - 1)) + 1;
		int endNum = pageItemPerPage * nowPage;

		log.info(startNum + "+" + endNum);

		bookDtos = adminBookDao.setList(search, sortSql, startNum, endNum);

		return bookDtos;
	}

	
	private String setSortSql(String sort) {
		String sortSql = "";

		switch (sort) {
		case "isbn":
			sortSql = "B_ISBN";
			break;
		case "name":
			sortSql = "B_NAME";
			break;
		case "author":
			sortSql = "B_AUTHOR";
			break;
		
		default:
			sortSql = "B_NO";
			break;
		}
		
		log.info("isbn" + sort);
		return sortSql;
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
	


}
