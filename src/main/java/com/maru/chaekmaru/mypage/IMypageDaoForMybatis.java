package com.maru.chaekmaru.mypage;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.shop.SaledBookDto;

@Mapper
public interface IMypageDaoForMybatis {
	
	public List<MemberCartDto> getMyCartList(String m_id);
	public int addBookCount(@Param("m_id") String m_id, @Param("c_no") int c_no, @Param("c_book_count") int c_book_count);
	public int addBookCountByBNo(@Param("m_id") String m_id, @Param("b_no") int b_no, @Param("count") int count);
	public int deleteMyCart(@Param("m_id") String m_id, @Param("c_no") int c_no);
	public int deleteMyCartByBNo(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public List<MemberCartDto> paymentForm(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public int paymentMyCart(@Param("saledBookDto") SaledBookDto  saledBookDto, @Param("m_id") String m_id);
	public int insertPoint(MyPointListDto myPointListDto);
	public List<MemberCartDto> allPaymentForm(String m_id);
	public int addMyCart(@Param("m_id") String m_id, @Param("b_no") int b_no, @Param("count") int count);
	public int selectBookCount(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public int allPaymentMyCartList(@Param("m_id") String m_id, @Param("saledBookDto") SaledBookDto saledBookDto);
	public int deleteAllMyCart(@Param("m_id") String m_id, @Param("selectBook") int selectBook);
	public BookDto selectBook(@Param("b_no") int b_no);
	public List<SaledBookDto> getPaymentList(String m_id);
//    public void nowBooks(@Param("sb_book_count") int sb_book_count, @Param("b_count") int b_count, @Param("b_no") int b_no);
	public List<MemberPickDto> myPickList(String m_id);
	public ArrayList<MyPointListDto> selectMyPointList(String m_id);
	public int selectBookCountBySbNo(@Param("sb_no") int sb_no);
	public int selectBookCountByBNo(@Param("b_no") int b_no);
	public int updateCancelBookCount(@Param("updateCancelBookCount") int updateCancelBookCount, @Param("b_no") int b_no);
	public int paymentPoint(@Param("m_id") String m_id, @Param("sb_no") int sb_no, @Param("b_no") int b_no);
	public int selectNowPoint(String m_id);
	public int insertReturnPoint(@Param("myPointListDto") MyPointListDto myPointListDto, @Param("paymentPoint") int paymentPoint, @Param("m_id") String m_id);
	public int saledStateUpdateZero(@Param("m_id") String m_id, @Param("sb_no") int sb_no, @Param("b_no") int b_no);
	public int deleteMyPick(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public int selectMaxSbOrderNo(String m_id);
	public int selectBookInMemberPick(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public int addMyPick(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public int sbOrderNoCount(String m_id);
	public List<MemberCartDto> getBookDetail(int b_no);
	public int saledStateUpdateThree(@Param("m_id") String m_id, @Param("sb_no") int sb_no, @Param("b_no") int b_no);
	public MemberCartDto selectBookData(@Param("b_no") int b_no);
	public int remainBooks(@Param("b_no") int b_no, @Param("remainBooks") int remainBooks);
	public int removeCartByBNo(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public int removePointByBuyBooks(@Param("m_id") String m_id, @Param("myPointListDto") MyPointListDto myPointListDto);
	public List<SaledBookDto> getOrderNo(String m_id);
	public ArrayList<SaledBookDto> getPaymentListByONo(@Param("m_id") String m_id, @Param("o_no") int o_no);
	
	public ArrayList<AttendenceDto> selectAttendenceList(String m_id);
	public int insertAttendence(@Param("m_id") String m_id, @Param("acc") int acc);
	public int selectAccAttendence(String m_id);
}