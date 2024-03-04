package com.maru.chaekmaru.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDaoForMybatis {
	
		public int isMember(String m_id);
		public int insertMember(MemberDto memberDto);
		public MemberDto selectMember(String m_id);
		public int updateMemberForModify(MemberDto memberDto);
		public int deleteMember(String m_id);

}
