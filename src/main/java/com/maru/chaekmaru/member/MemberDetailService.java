package com.maru.chaekmaru.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailService implements UserDetailsService {

	@Autowired
	IMemberDaoForMybatis iMemberDaoForMybatis;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		MemberDto selectedMemberDto = iMemberDaoForMybatis.selectMember(username);
		
		return User.builder()
				.username(selectedMemberDto.getM_id())
				.password(selectedMemberDto.getM_pw())
				.roles("USER")
				.build();
		
	}

}
	