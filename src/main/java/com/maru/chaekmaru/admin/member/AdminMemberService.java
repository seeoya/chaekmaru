package com.maru.chaekmaru.admin.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.config.Config;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminMemberService {

	@Autowired
	IAdminMemberDaoForMybatis adminMemberDao;
	
	public int createAccountConfirm(AdminMemberDto adminMemberDto) {
		log.info("createAccountConfirm()");
		
		boolean isAdminMember = adminMemberDao.isAdminMember(adminMemberDto.getA_id());
		
		if(!isAdminMember) {
			int result = adminMemberDao.insertAdminAccount(adminMemberDto);
			
			if(result > 0)
				return Config.ADMIN_CREATE_ACCOUNT_SUCCESS;
			else 
				return Config.ADMIN_CREATE_ACCOUNT_FAIL;
			
		} else {
			return Config.ADMIN_ID_ALREADY_EXIST;
		}
		
	}

	public AdminMemberDto loginConfirm(AdminMemberDto adminMemberDto) {
		log.info("loginConfirm()");
		
		return adminMemberDao.selectAdminForLogin(adminMemberDto);
	}

	public AdminMemberDto modifyAccountConfirm(AdminMemberDto adminMemberDto) {
		log.info("modifyAccountConfirm");
		
		int result = adminMemberDao .updateAdminForModify(adminMemberDto);
		
		if(result > 0)
			
			return adminMemberDao.selectAdminForModify(adminMemberDto.getA_no());
						
		else 
			
			return null;
		
	}

	public int deleteAccountConfirm(int a_no) {
		log.info("deleteAccountConfirm");
		
		int result = adminMemberDao.deleteAdminAccount(a_no);
		
		if(result > 0)
			return Config.ADMIN_MEMBER_DELETE_SUCCESS;
		else 
			return Config.ADMIN_MEMBER_DELETE_FAIL;		
		
	}

	public List<AdminMemberDto> adminListForm() {
		log.info("adminListForm()");
		
		return adminMemberDao.selectAllAdmins();
	}

	public int setAdminApproval(int a_no) {
		log.info("setAdminApproval");
		
		int result = adminMemberDao.updateAdminApproval(a_no);
		
		if(result > 0)
			return Config.SET_ADMIN_APPROVAL_SUCCESS_AT_DATABASE;
		else 
			return Config.SET_ADMIN_APPROVAL_FAIL_AT_DATABASE;		
	}

}
