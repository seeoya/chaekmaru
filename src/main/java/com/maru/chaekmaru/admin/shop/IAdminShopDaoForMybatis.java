package com.maru.chaekmaru.admin.shop;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.maru.chaekmaru.book.BookDto;
import com.maru.chaekmaru.member.MemberDto;
import com.maru.chaekmaru.mypage.MyPointListDto;
import com.maru.chaekmaru.shop.SaledBookDto;

@Mapper
public interface IAdminShopDaoForMybatis {

	public List<MyPointListDto> selectAllUserPointsForSum();
	
	public MyPointListDto selectUserPointForModify(String m_id);
	
	public int insertPointForMyPointList(MyPointListDto myPointListDto);

	public List<MyPointListDto> selectUserPointForHistory(String m_id);

	public List<MemberDto> selectAllUsers();

	public int updateActiveForUserState(String m_id);

	public List<SaledBookDto> selectAllSaledBooks();
	
	public List<AdminShopDto> selectOrderBookCNTs();

	public SaledBookDto selectSaledBookForDetail(int sb_no);

	public List<SaledBookDto> selectAllReturnBooks();
	
	public List<SaledBookDto> selectAllReturnBooksForHistory();
	
	public SaledBookDto selectReturnBookForDetail(int sb_no);

	public int updateApprovalForReturnBook(int sb_no);
	
	public int updateBookCount(int b_no, int sb_book_count);	

	public int updateNotApprovedForReturnBook(int sb_no);	

	public BookDto selectBookForInventory(int b_no);

	public int updateBookInventory(BookDto bookDto);

	public List<SaledBookDto> selectSaledBooksByPeriod(String saled_start, String saled_end);

	public int countListResult();

	public ArrayList<BookDto> setList(@Param("startNum") int startNum, @Param("endNum") int endNum);

	public int returnPoint(int sb_no);

	public String getMId(int sb_no);
	
	public int insertRetrunPayment(MyPointListDto myPointListDto);

	public int selectSbAllPointBySbNo(int sb_no);

	public int selectSalePointBySbNo(int sb_no);
	

}
