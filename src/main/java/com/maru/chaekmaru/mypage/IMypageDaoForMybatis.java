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
	public List<MemberCartDto> paymentForm(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public int insertPoint(MyPointListDto myPointListDto);
	public List<MemberCartDto> allPaymentForm(String m_id);
	public int addMyCart(@Param("m_id") String m_id, @Param("b_no") int b_no, @Param("count") int count);
	public int selectBookCount(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public int allPaymentMyCartList(@Param("m_id") String m_id, @Param("saledBookDto") SaledBookDto saledBookDto);
	public BookDto selectBook(@Param("b_no") int b_no);
	public List<SaledBookDto> getPaymentList(String m_id);
	public List<MemberPickDto> myPickList(String m_id);
	public ArrayList<MyPointListDto> selectMyPointList(String m_id);
	public int selectBookCountByBNo(@Param("sb_order_no") int sb_order_no, @Param("b_no") int b_no);
	public int updateCancelBookCount(@Param("updateCancelBookCount") int updateCancelBookCount, @Param("selectBNo") int selectBNo);
	public int paymentPoint(@Param("m_id") String m_id, @Param("sb_order_no") int sb_order_no);
	public int selectNowPoint(String m_id);
	public int insertReturnPoint(@Param("myPointListDto") MyPointListDto myPointListDto, @Param("returnPoint") int returnPoint, @Param("m_id") String m_id);
	public int saledStateUpdateZero(@Param("m_id") String m_id, @Param("sb_order_no") int sb_order_no);
	public int deleteMyPick(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public int selectMaxSbOrderNo(String m_id);
	public int selectBookInMemberPick(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public int addMyPick(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public int sbOrderNoCount(String m_id);
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
	public int sumAllBook(@Param("m_id") String m_id, @Param("orderNo") int orderNo);
	public int selectSalePrice(@Param("m_id") String m_id, @Param("sb_order_no") int sb_order_no);
	public int sumSbAllPointByMId(String m_id);
	public int sumSbSalePointByMId(String m_id);
	public int updateGrade(@Param("m_id") String m_id, @Param("state") int state);
	public MemberPickDto isMemberPick(@Param("m_id") String m_id, @Param("b_no") int b_no);
	public List<SaledBookDto> selectMyPaymentBySbON(@Param("sb_order_no") int sb_order_no, @Param("m_id") String m_id);
	public int sumPaymentBookBySbON(@Param("m_id") String m_id, @Param("sb_order_no") int sb_order_no);
	public int confirmPayment(@Param("m_id") String m_id, @Param("sb_order_no") int sb_order_no);
}