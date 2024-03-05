package com.maru.chaekmaru.book;

import java.awt.print.Book;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
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

	public void paging() {

	}

	public ArrayList<BookDto> setList(String sort, int pageInt, String search) {

		int startNum = 0;
		int endNum = 5;
		String sortSql = "B_NAME ASC";

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
			break;
		case "name_asc":
			break;
		case "name_desc":
			break;
		default:
			break;
		}

		log.info("1111111111111" + search);

		ArrayList<BookDto> bookDtos = bookDao.setList(sortSql, startNum, endNum, search);

		return bookDtos;
	}

}
