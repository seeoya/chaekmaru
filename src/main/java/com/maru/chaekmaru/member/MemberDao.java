package com.maru.chaekmaru.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.maru.chaekmaru.config.Config;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class MemberDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public boolean isMember(String m_id) {
		log.info("--isMember--");
		
		String sql = "SELECT COUNT(*) FROM TBL_MEMBER WHERE M_ID = ?";
		
		boolean isMember = false;
		
		try {
			
			int result = jdbcTemplate.queryForObject(sql, Integer.class, m_id);
			if (result > 0)
				isMember = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return isMember;

	}

	public int insertMember(MemberDto memberDto) {
		log.info("--insertMember--");
			
		String sql =  "INSERT INTO TBL_MEMBER("
				+ 	"M_ID, "
				+	"M_PW, "
				+ 	"M_NAME, "
				+ 	"M_MAIL, "
				+ 	"M_PHONE, "
				+ 	"M_ADDR_CODE, "
				+ 	"M_ADDR, "
				+ 	"M_DETAIL_ADDR, "
				+ 	"M_REG_DATE, "
				+ 	"M_MOD_DATE) "
				+ "VALUES(?, ?, ?, ?, ? , ? , ? , ?, SYSTIMESTAMP, SYSTIMESTAMP)";
			
			int result = Config.DATABASE_COMMUNICATION_TROUBLE;
			
			try {
				
				result = jdbcTemplate.update(sql, 
									memberDto.getM_id(), 
									memberDto.getM_pw(),
									memberDto.getM_name(),
									memberDto.getM_mail(),
									memberDto.getM_phone(),
									memberDto.getM_addr_code(),
									memberDto.getM_addr(),
									memberDto.getM_detail_addr());
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			
			return result;
			
	}

	public MemberDto memberLoginConfirm(MemberDto memberDto) {
		log.info("memberLoginConfirm()");
		
		String sql = "SELECT * FROM TBL_MEMBER WHERE M_ID = ?";
		
		List<MemberDto> memberDtos = new ArrayList<>();
		
		try {
			
			RowMapper<MemberDto> rowMapper = 
					BeanPropertyRowMapper.newInstance(MemberDto.class);
			memberDtos = jdbcTemplate.query(sql, rowMapper, memberDto.getM_id());
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return memberDtos.size() > 0 ? memberDtos.get(0) : null;
	}

	public int updateMemberForModify(MemberDto memberDto) {
log.info("updateMemberForModify()");
		
		String sql =  "UPDATE "
					+ 	"TBL_MEMBER "
					+ "SET "
					+ 	"M_NAME = ?, "
					+ 	"M_MAIL= ?, "
					+ 	"M_PHONE = ?, "
					+ 	"M_ADDR_CODE = ?, "
					+ 	"M_ADDR = ?, "
					+ 	"M_DETAIL_ADDR =?, "
					+ 	"M_MOD_DATE = SYSTIMESTAMP "
					+ "WHERE "
					+ 	"M_ID = ?";
		
		int result = -1;
		
		try {
			
			result = jdbcTemplate.update(sql, 
											memberDto.getM_name(),
											memberDto.getM_mail(), 
											memberDto.getM_phone(),
											memberDto.getM_addr_code(),
											memberDto.getM_addr(),
											memberDto.getM_detail_addr(),
											memberDto.getM_id());
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		return result;
	}

	public MemberDto getLatestMemberInfo(MemberDto memberDto) {
		log.info("getLatesMemberInfo()");
		
		String sql = "SELECT * FROM TBL_MEMBER WHERE M_ID = ?";
		
		List<MemberDto> memberDtos = new ArrayList<>();
		
		try {
			
			RowMapper<MemberDto> rowMapper =
					BeanPropertyRowMapper.newInstance(MemberDto.class);
			memberDtos = jdbcTemplate.query(sql, rowMapper, memberDto.getM_id());
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return memberDtos.size() > 0 ? memberDtos.get(0) : null;
		
	}

	public int deleteMember(String m_id) {
		log.info("deleteMember()");
		
		String sql =  "DELETE FROM TBL_MEMBER "
				+ "WHERE M_ID = ?";
	
		int result = -1;
		
		try {
			
			result = jdbcTemplate.update(sql, m_id);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return result;
		
		
	
		}

}
