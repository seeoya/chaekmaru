package com.maru.chaekmaru.member;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class MemberDao {
	
	
	public void isMember(String m_id) {
		log.info("--isMember--");
		
	}

}
