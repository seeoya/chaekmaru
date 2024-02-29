package com.maru.chaekmaru.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MemberService {

	@Autowired
	MemberDao memberDao;
	
	public int memberAccountConfirm(MemberDto memberDto) {
		log.info("--memberAccountConfirm--");
		
		memberDao.isMember(memberDto.getM_id());
		
		
		
		
		return 0;
	}

}
