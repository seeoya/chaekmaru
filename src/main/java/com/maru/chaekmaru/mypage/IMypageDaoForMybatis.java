package com.maru.chaekmaru.mypage;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.shop.SaledBookDto;

@Mapper
public interface IMypageDaoForMybatis {
	
	public List<MemberCartDto> getMyCartList(String m_id);
	public int addBookCount(@Param("m_id") String m_id, @Param("c_no") int c_no, @Param("c_book_count") int c_book_count);
	public int addBookCountByBNo(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public int deleteMyCart(@Param("m_id") String m_id, @Param("c_no") int c_no);
	public int deleteMyCartByBNo(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public List<MemberCartDto> paymentForm(@Param("m_id") String m_id, @Param("c_no") int c_no);
	public int paymentMyCart(@Param("saledBookDto") SaledBookDto  saledBookDto, @Param("m_id") String m_id);
	public int insertPoint(MyPointListDto myPointListDto);
	public List<MemberCartDto> allPaymentForm(String m_id);
	public int addMyCart(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public int selectBookCount(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public int allPaymentMyCartList(@Param("m_id") String m_id, @Param("saledBookDto") SaledBookDto saledBookDto);
	public int deleteAllMyCart(String m_id);
	public BookDto selectBook(@Param("b_no") int b_no);
	public List<SaledBookDto> getPaymentList(String m_id);

}
