package com.maru.chaekmaru.admin.member;

import org.springframework.stereotype.Service;

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
				return Config.INSERT_SUCCESS_AT_DATABASE;
			else 
				return Config.INSERT_FAIL_AT_DATABASE;
			
		} else {
			return Config.ID_ALREADY_EXIST;
		}
		
	}

	public AdminMemberDto loginConfirm(AdminMemberDto adminMemberDto) {
		log.info("loginConfirm()");
		
		return adminMemberDao.selectAdmin(adminMemberDto);
	}

	public int modifyAccountConfirm(AdminMemberDto adminMemberDto) {
		log.info("modifyAccountConfirm");
		
		return adminMemberDao.updateAdminForModify(adminMemberDto);
	}

	public int deleteAccountConfirm(int a_no) {
		log.info("deleteAccountConfirm");
		
		int result = adminMemberDao.deleteAdminAccount(a_no);
		
		if(result > 0)
			return Config.DELETE_SUCCESS_AT_DATABASE;
		else 
			return Config.DELETE_FAIL_AT_DATABASE;		
		
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
