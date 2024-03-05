package com.maru.chaekmaru.admin.book;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.shop.SaledBookDto;

@Mapper
public interface IAdminBookDaoForMybatis {

	public boolean isBook(String b_isbn);

	public int insertBook(BookDto bookDto);

	public List<SaledBookDto> selectAllBooks();
	
	

}
