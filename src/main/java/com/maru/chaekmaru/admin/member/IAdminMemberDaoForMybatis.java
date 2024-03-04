package com.maru.chaekmaru.admin.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAdminMemberDaoForMybatis {
	
	public boolean isAdminMember(String a_id);
	public int insertAdminAccount(AdminMemberDto adminMemberDto);
	public AdminMemberDto selectAdmin(AdminMemberDto adminMemberDto);	
	public int updateAdminForModify(AdminMemberDto adminMemberDto);
	public int deleteAdminAccount(int a_no);
	public List<AdminMemberDto> selectAllAdmins();
	public int updateAdminApproval(int a_no);
}
