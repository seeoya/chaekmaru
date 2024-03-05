package com.maru.chaekmaru.book;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2

public class BookService {

	@Autowired
	IBookDao bookDao;

	public ArrayList<BookDto> recommendItem() {
		int randomNo = (int) (Math.random() * 5000) + 1;
		int randomNo2 = (int) (Math.random() * 5000) + 1;

		ArrayList<BookDto> recommendBookDtos = new ArrayList<>();
		recommendBookDtos.add(bookDao.selectBook(randomNo));
		recommendBookDtos.add(bookDao.selectBook(randomNo2));

		if (recommendBookDtos != null) {
			return recommendBookDtos;
		} else {
			return null;
		}
	}

	public ArrayList<BookDto> bestItem(int count) {
		ArrayList<BookDto> bestBookDtos = bookDao.selectBestBooksByStar(count);

		if (bestBookDtos != null) {
			return bestBookDtos;
		} else {
			return null;
		}
	}

	public ArrayList<BookDto> newItem(int count) {
		ArrayList<BookDto> newBookDtos = bookDao.selectNewBooks(count);

		if (newBookDtos != null) {
			return newBookDtos;
		} else {
			return null;
		}
	}

	public ArrayList<BookDto> setList(String sort, int pageItem, int nowPage, String search) {
		ArrayList<BookDto> bookDtos = new ArrayList<>();
		String sortSql = setSortSql(sort);

		int startNum = (pageItem * (nowPage - 1)) + 1;
		int endNum = pageItem * nowPage;

		log.info(startNum + "+" + endNum);

		bookDtos = bookDao.setList(sortSql, startNum, endNum, search);

		return bookDtos;
	}

	private String setSortSql(String sort) {
		String sortSql = "";

		switch (sort) {
		case "new":
			sortSql = "B_REG_DATE DESC";
			break;
		case "old":
			sortSql = "B_REG_DATE ASC";
			break;
		case "best":
			break;
		case "sales":
			break;
		case "price_low":
			sortSql = "B_PRICE ASC";
			break;
		case "price_high":
			sortSql = "B_PRICE DESC";
			break;
		case "name_asc":
			sortSql = "B_NAME ASC";
			break;
		case "name_desc":
			sortSql = "B_NAME DESC";
			break;
		default:
			sortSql = "B_REG_DATE DESC";
			break;
		}

		return sortSql;
	}

	public ArrayList<ListPageDto> setPaging(int pageItem, int nowPage, String search) {
		ArrayList<ListPageDto> listPageDtos = new ArrayList<>();

		int count = bookDao.countListResult(search);
		int allPage = (int) Math.ceil((double) count / pageItem);

		if (nowPage != 1) {
			listPageDtos.add(new ListPageDto("start", "start", 1));
		}

		if (nowPage - 4 > 0 && nowPage + 2 > allPage) {
			listPageDtos.add(new ListPageDto("num", "num", nowPage - 4));
		}

		if (nowPage - 3 > 0 && nowPage + 1 > allPage) {
			listPageDtos.add(new ListPageDto("num", "num", nowPage - 3));
		}

		if (nowPage - 2 > 0) {
			listPageDtos.add(new ListPageDto("num", "num", nowPage - 2));
		}

		if (nowPage - 1 > 0) {
			listPageDtos.add(new ListPageDto("num", "num", nowPage - 1));
		}

		listPageDtos.add(new ListPageDto("num", "num now", nowPage));

		if (nowPage + 1 <= allPage) {
			listPageDtos.add(new ListPageDto("num", "num", nowPage + 1));
		}

		if (nowPage + 2 <= allPage) {
			listPageDtos.add(new ListPageDto("num", "num", nowPage + 2));
		}

		if (nowPage + 3 <= allPage && nowPage - 1 <= 0) {
			listPageDtos.add(new ListPageDto("num", "num", nowPage + 3));
		}

		if (nowPage + 4 <= allPage && nowPage - 2 <= 0) {
			listPageDtos.add(new ListPageDto("num", "num", nowPage + 4));
		}

		if (nowPage != allPage) {
			listPageDtos.add(new ListPageDto("end", "end", allPage));
		}

		return listPageDtos;
	}

}
