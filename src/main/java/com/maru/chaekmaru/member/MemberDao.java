package com.maru.chaekmaru.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

}
