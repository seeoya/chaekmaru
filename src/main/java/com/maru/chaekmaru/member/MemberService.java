package com.maru.chaekmaru.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.config.Config;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MemberService {

	@Autowired
	IMemberDaoForMybatis memberDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	public int memberAccountConfirm(MemberDto memberDto) {
		log.info("--memberAccountConfirm--");
		
		int isMember = memberDao.isMember(memberDto.getM_id());
		
		if (isMember <= 0) {
			memberDto.setM_pw(passwordEncoder.encode(memberDto.getM_pw()));
			int result = memberDao.insertMember(memberDto);
			
			switch (result) {
			case Config.DATABASE_COMMUNICATION_TROUBLE:
				log.info("DATABASE COMMUNICATION TROUBLE");
				break;

			case Config.INSERT_FAIL_AT_DATABASE:
				log.info("INSERT FAIL AT DATABASE");
				break;
				
			case Config.INSERT_SUCCESS_AT_DATABASE:
				log.info("INSERT SUCCESS AT DATABASE");
				break;

			}
			
			return result;
			
		} else {
			
			return Config.ID_ALREADY_EXIST;
			
		}
		
	}

	/*
	 * public MemberDto loginConfirm(MemberDto memberDto) {
	 * log.info("--loginConfirm--");
	 * 
	 * MemberDto selectedMemberDtoById =
	 * memberDao.selectMember(memberDto.getM_id());
	 * 
	 * if (selectedMemberDtoById != null &&
	 * passwordEncoder.matches(memberDto.getM_pw(),
	 * selectedMemberDtoById.getM_pw())) {
	 * log.info(selectedMemberDtoById.getM_id());
	 * 
	 * return selectedMemberDtoById; } else { return null; } }
	 */

	public MemberDto modifyConfirm(MemberDto memberDto) {

		log.info("modifyConfirm()");
		
		int result = memberDao.updateMemberForModify(memberDto);
		
		if (result > 0) {
			return memberDao.selectMember(memberDto.getM_id());
		}
			
		return null;
	}

	public int memberDeleteConfirm(String m_id) {
		log.info("memberDeleteConfirm()");
		
		return memberDao.deleteMember(m_id);
	}

	public String findIdByNameAndEmail(String name, String email) {
		log.info("findIdByNameAndEmail()");
		String id = memberDao.findIdByNameAndEmail(name, email);
		return id;
	}

	public void sendEmail(String email, String message) {
		log.info("sendEmail()");
		SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("the result of your request");
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);
    }

	public MemberDto thereIsId(String id, String name, String email) {
		log.info("thereIsId()");
		return (MemberDto) memberDao.selectthereIsId(id,name,email);
	}

	public int pwModifyConfirm(String id, String m_pw) {
		log.info("pwModifyConfirm()");
		
		int result = memberDao.pwModifyConfirm(id,passwordEncoder.encode(m_pw));
		
		return result;
	}
	

}


