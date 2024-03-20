package com.maru.chaekmaru.admin.book;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.maru.chaekmaru.book.BookDto;

@Mapper
public interface IAdminBookDaoForMybatis {

	public boolean isBook(String b_isbn);

	public int insertBook(BookDto bookDto);

	public BookDto selectBookForModify(int b_no);

	public int updateBookForModify(BookDto bookDto);

	public int deleteBookConfirm(int b_no);

	public int countListResult(@Param("search") String search, @Param("sortSql") String sortSql);

	public ArrayList<BookDto> setList(@Param("search") String search, @Param("sortSql") String sortSql,
			@Param("startNum") int startNum, @Param("endNum") int endNum);

	public int deleteReview(int b_no);
		

}
