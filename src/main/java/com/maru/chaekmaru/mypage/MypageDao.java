package com.maru.chaekmaru.mypage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.maru.chaekmaru.shop.SaledBookDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class MypageDao {
	
//	@Autowired
//	JdbcTemplate jdbcTemplate;
//	
//	public List<MemberCartDto> getMyCartList(String m_id) {
//		log.info("getMyCartList");
//		
//		String sql = "SELECT * FROM "
//					+ "TBL_MEMBER_CART MC "
//					+ "JOIN TBL_BOOK B "
//					+ "ON MC.B_NO = B.B_NO "
//					+ "JOIN TBL_MEMBER M "
//					+ "ON MC.M_ID = M.M_ID "
//					+ "WHERE MC.M_ID = ? "
//					+ "ORDER BY MC.C_NO DESC";
//		
//		List<MemberCartDto> memberCartDtos = new ArrayList<>();
//		
//		try {
//			
//			RowMapper<MemberCartDto> rowMapper = 
//					BeanPropertyRowMapper.newInstance(MemberCartDto.class);
//			memberCartDtos = jdbcTemplate.query(sql, rowMapper, m_id);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//		
//		return memberCartDtos.size() > 0 ? memberCartDtos : null;
//		
//	}
//	
////	public int addMyCart(String m_id) {
////		log.info("addMyCart");
////		
//////		String sql = "INSERT INTO "
//////					+ "TBL_MEMBER_CART MC"
//////					+ "JOIN TBL_BOOK B "
//////					+ "ON MC.B_NO = B.B_NO "
//////					+ "JOIN TBL_MEMBER M "
//////					+ "ON MC.M_ID = M.M_ID "
//////					+ "WHERE MC.M_ID = ? "
//////					+ "ORDER BY MC.C_NO DESC";
////		
////		String sql = "INSERT INTO "
////					+ "TBL_MEMBER_CART MC "
////					+ "JOIN TBL_BOOK B "
////					+ "ON MC.B_NO = B.B_NO "
////					+ "JOIN TBL_MEMBER M "
////					+ "ON MC.M_ID = M.M_ID "
////					+ "WHERE MC.M_ID = ? "
////					+ "ORDER BY MC.C_NO DESC";
////		
////		int result = -1;
////		
////		try {
////			
////			result = jdbcTemplate.update(sql, m_id);
////			
////		} catch (Exception e) {
////			e.printStackTrace();
////			
////		}
////		
////		return result;
////		
////	}
//
//	public int deleteMyCart(String m_id, int c_no) {
//		log.info("deleteMyCart");
//		
//		String sql = "DELETE FROM "
//					+ "TBL_MEMBER_CART "
//					+ "WHERE M_ID = ? "
//					+ "AND C_NO = ? ";
//		
//		int result = -1;
//		
//		try {
//			
//			result = jdbcTemplate.update(sql, m_id, c_no);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//		
//		return result;
//		
//	}
//
//	public List<MemberCartDto> paymentForm(String m_id, int c_no) {
//		log.info("getMyCartList");
//		
//		String sql = "SELECT * FROM "
//					+ "TBL_MEMBER_CART MC "
//					+ "JOIN TBL_BOOK B "
//					+ "ON MC.B_NO = B.B_NO "
//					+ "JOIN TBL_MEMBER M "
//					+ "ON MC.M_ID = M.M_ID "
//					+ "WHERE MC.M_ID = ? "
//					+ "AND MC.C_NO = ?";
//	
//		List<MemberCartDto> memberCartDtos = new ArrayList<>();
//		
//		try {
//			
//			RowMapper<MemberCartDto> rowMapper = 
//					BeanPropertyRowMapper.newInstance(MemberCartDto.class);
//			memberCartDtos = jdbcTemplate.query(sql, rowMapper, m_id, c_no);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//		
//		return memberCartDtos.size() > 0 ? memberCartDtos : null;
//	
//	}
//
//	public int paymentMyCart(String m_id, SaledBookDto saledBookDto) {
//		log.info("paymentMyCart");
//		
//		String sql = "INSERT INTO "
//					+ "TBL_SALED_BOOK("
//					+ "B_NO, "
//					+ "M_ID, "
//					+ "SB_NUMBER, "
//					+ "SB_NAME, "
//					+ "SB_ADDR_CODE, "
//					+ "SB_ADDR, "
//					+ "SB_DETAIL_ADDR, "
//					+ "SB_MEMO, "
//					+ "SB_ALL_PRICE, "
//					+ "SB_STATE, "
//					+ "SB_REG_DATE, "
//					+ "SB_MOD_DATE) "
//					+ "VALUES("
//					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, 1, SYSTIMESTAMP, SYSTIMESTAMP)";
//		
//		int result = -1;
//		
//		try {
//			
//			result = jdbcTemplate.update(sql,
//											saledBookDto.getB_no(),
//											m_id,
//											saledBookDto.getSb_number(),
//											saledBookDto.getSb_name(),
//											saledBookDto.getSb_addr_code(),
//											saledBookDto.getSb_addr(),
//											saledBookDto.getSb_detail_addr(),
//											saledBookDto.getSb_memo(),
//											saledBookDto.getSb_all_price());
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//		
//		log.info("result ========>" + result);
//		
//		return result;
//		
//	}

}
