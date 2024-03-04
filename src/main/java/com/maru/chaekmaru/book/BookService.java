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

		ArrayList<BookDto> recommendBookDtos = bookDao.selectBook(randomNo, randomNo2);

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

}
