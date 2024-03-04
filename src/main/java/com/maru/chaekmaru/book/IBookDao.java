package com.maru.chaekmaru.book;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IBookDao {
	
	public ArrayList<BookDto> selectBook(@Param("b_no") int b_no, @Param("b_no2") int b_no2);
	public ArrayList<BookDto> selectBestBooksByStar(int count);
	public ArrayList<BookDto> selectBestBooksByReviewCount(int count);
	public ArrayList<BookDto> selectNewBooks(int count);
	

}
