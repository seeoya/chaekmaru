package com.maru.chaekmaru.mypage;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.maru.chaekmaru.shop.SaledBookDto;

@Mapper
public interface IMypageDaoForMybatis {
	
	public List<MemberCartDto> getMyCartList(String m_id);
	public int addBookCount(@Param("m_id") String m_id, @Param("c_no") int c_no, @Param("c_book_count") int c_book_count);
	public int deleteMyCart(@Param("m_id") String m_id, @Param("c_no") int c_no);
	public List<MemberCartDto> paymentForm(@Param("m_id") String m_id, @Param("c_no") int c_no);
	public int paymentMyCart(@Param("saledBookDto")SaledBookDto  saledBookDto, @Param("m_id") String m_id);

}
