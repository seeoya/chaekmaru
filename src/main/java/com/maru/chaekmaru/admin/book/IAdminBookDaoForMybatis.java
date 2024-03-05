package com.maru.chaekmaru.admin.book;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.shop.SaledBookDto;

@Mapper
public interface IAdminBookDaoForMybatis {

	public boolean isBook(String b_isbn);

	public int insertBook(BookDto bookDto);

	public List<BookDto> selectAllBooks();

	public BookDto selectBookForModify(int b_no);

	public int updateBookForModify(BookDto bookDto);

	public int deleteBookConfirm(int b_no);
	
	

}
