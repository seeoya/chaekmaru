package com.maru.chaekmaru.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IMemberDaoForMybatis {
	
		public int isMember(String m_id);
		public int insertMember(MemberDto memberDto);
		public MemberDto selectMember(String m_id);
		public int updateMemberForModify(MemberDto memberDto);
		public int leaveMember(String m_id);
		public String findIdByNameAndEmail(@Param("name") String name,@Param("email") String email);
		public MemberDto selectMemberByFindPw(@Param("id") String id, @Param("name") String name,@Param("email") String email);
		public int pwModifyConfirm(@Param("id") String id, @Param("m_pw") String m_pw);
		public int selectNowPoint(String m_id);
		public int deleteReview(String m_id);
		public int deleteCart(String m_id);
		public int deletePick(String m_id);
		public int deleteAttend(String m_id);
		public int getMemberGrade(String m_id);
		public int selectMState(String m_id);

}
