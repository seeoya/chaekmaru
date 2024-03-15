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

	public ArrayList<BookDto> recommendItem(int count) {

		ArrayList<BookDto> recommendBookDtos = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			recommendBookDtos.add(bookDao.selectBook((int) (Math.random() * 5000) + 1));
		}

		if (recommendBookDtos.size() > 0) {
			return recommendBookDtos;
		} else {
			return null;
		}
	}

	public ArrayList<BookDto> bestItem(int count) {
		ArrayList<BookDto> bestBookDtos = bookDao.selectBestBooksByStar(count);

		if (bestBookDtos.size() > 0) {
			return bestBookDtos;
		} else {
			return null;
		}
	}

	public ArrayList<BookDto> newItem(int count) {
		ArrayList<BookDto> newBookDtos = bookDao.selectNewBooks(count);

		if (newBookDtos.size() > 0) {
			return newBookDtos;
		} else {
			return null;
		}
	}

	public ArrayList<BookDto> setList(String search, String filter, String sort, int pageItemPerPage, int nowPage) {
		ArrayList<BookDto> bookDtos = new ArrayList<>();
		String filterSql = setfilterSql(filter);
		String sortSql = setSortSql(sort);

		int startNum = (pageItemPerPage * (nowPage - 1)) + 1;
		int endNum = pageItemPerPage * nowPage;

		log.info(startNum + "+" + endNum);

		bookDtos = bookDao.setList(search, filterSql, sortSql, startNum, endNum);

		if (bookDtos.size() > 0) {
			return bookDtos;
		} else {
			return null;
		}
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
			sortSql = "R_AVG DESC NULLS LAST";
			break;
		case "review":
			sortSql = "R_COUNT DESC NULLS LAST";
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

	private String setfilterSql(String filter) {
		String filterSql = "";

		switch (filter) {
		case "0":
			filterSql = "AND B.B_KDC BETWEEN 0 AND 99";
			break;
		case "1":
			filterSql = "AND B.B_KDC BETWEEN 100 AND 199";
			break;
		case "2":
			filterSql = "AND B.B_KDC BETWEEN 200 AND 299";
			break;
		case "3":
			filterSql = "AND B.B_KDC BETWEEN 300 AND 399";
			break;
		case "4":
			filterSql = "AND B.B_KDC BETWEEN 400 AND 499";
			break;
		case "5":
			filterSql = "AND B.B_KDC BETWEEN 500 AND 599";
			break;
		case "6":
			filterSql = "AND B.B_KDC BETWEEN 600 AND 699";
			break;
		case "7":
			filterSql = "AND B.B_KDC BETWEEN 700 AND 799";
			break;
		case "8":
			filterSql = "AND B.B_KDC BETWEEN 800 AND 899";
			break;
		case "9":
			filterSql = "AND B.B_KDC BETWEEN 900 AND 999";
			break;
		default:
			filterSql = "";
			break;
		}

		return filterSql;
	}

	public ArrayList<ListPageDto> setPaging(int pageItemPerPage, int nowPageCount, int allPageCount) {
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

	public int countBook(String search, String filter) {
		String filterSql = setfilterSql(filter);
		int count = bookDao.countListResult(search, filterSql);

		return count;
	}

	public int countAllPage(int count, int pageItemPerPage) {
		return (int) Math.ceil((double) count / pageItemPerPage);
	}

	public BookDto setView(int b_no) {
		return bookDao.selectBook(b_no);
	}

}
