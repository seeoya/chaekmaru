package com.maru.chaekmaru.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.mypage.IMypageDaoForMybatis;
import com.maru.chaekmaru.mypage.MyPointListDto;
import com.maru.chaekmaru.mypage.MypageService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
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

	@Autowired
	IMypageDaoForMybatis mypageDao;

	public int createAccountConfirm(MemberDto memberDto) {
		log.info("--createAccountConfirm--");

		int isMember = memberDao.isMember(memberDto.getM_id());

		if (isMember <= 0) {
			memberDto.setM_pw(passwordEncoder.encode(memberDto.getM_pw()));

			int result = memberDao.insertMember(memberDto);

			if (result > 0) {
				return Config.CREATE_ACCOUNT_SUCCESS;
			} else {
				return Config.CREATE_ACCOUNT_FAIL;
			}

		} else {
			return Config.ID_ALREADY_EXIST;
		}
	}

	public int isMember(String m_id) {
		log.info("--isMember--");
		log.info(m_id);

		return memberDao.isMember(m_id);
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

		// #TODO isMember
		int isMember = memberDao.isMember(memberDto.getM_id());

		if (isMember > 0) {
			int result = memberDao.updateMemberForModify(memberDto);

			if (result > 0) {
				return memberDao.selectMember(memberDto.getM_id());
			}
		} 

		return null;
	}

	public int memberDeleteConfirm(String m_id) {
		log.info("memberDeleteConfirm()");

		// #TODO isMember
		if (memberDao.isMember(m_id) > 0) {
			int nowPoint = mypageDao.selectNowPoint(m_id);
			int result = memberDao.leaveMember(m_id);

			if (result > 0) {
				MyPointListDto myPointListDto = new MyPointListDto();
				myPointListDto.setM_id(m_id);
				myPointListDto.setPl_payment_book_point(nowPoint * -1);
				myPointListDto.setPl_desc("회원 탈퇴");

				mypageDao.insertPoint(myPointListDto);

				memberDao.deleteReview(m_id);
				memberDao.deleteCart(m_id);
				memberDao.deletePick(m_id);
				memberDao.deleteAttend(m_id);

				return Config.MEMBER_DELETE_SUCCESS;
			} else {
				return Config.MEMBER_DELETE_FAIL;
			}
		} else {
			return Config.MEMBER_NOT_FOUND;
		}
	}

	public String findIdByNameAndEmail(String name, String email) {
		log.info("findIdByNameAndEmail()");

		return memberDao.findIdByNameAndEmail(name, email);
	}

	public void sendIdEmail(String email, String htmlContent) {
		log.info("sendEmail()");

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(email);
			helper.setSubject("'북마루' 아이디 찾기 메일입니다.");
			helper.setText(htmlContent, true); // 'true'는 HTML 메일을 보내겠다는 의미입니다.
		} catch (MessagingException e) {
			log.error("이메일 전송 중 에러 발생", e);
		}

		javaMailSender.send(mimeMessage);
	}

	public void sendEmail(String email, String htmlContent) {
		log.info("sendEmail()");

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(email);
			helper.setSubject("'북마루' 비밀번호 변경 메일입니다.");
			helper.setText(htmlContent, true); // 'true'는 HTML 메일을 보내겠다는 의미입니다.
		} catch (MessagingException e) {
			log.error("이메일 전송 중 에러 발생", e);
		}

		javaMailSender.send(mimeMessage);
	}

	public MemberDto findMember(String id, String name, String email) {
		log.info("findMember()");
		return (MemberDto) memberDao.selectMemberByFindPw(id, name, email);
	}

	public int pwModifyConfirm(String id, String m_pw) {
		log.info("pwModifyConfirm()");

		int result = memberDao.pwModifyConfirm(id, passwordEncoder.encode(m_pw));

		if (result > 0) {
			return Config.PW_MODIFY_SUCCESS;
		} else {
			return Config.PW_MODIFY_FAIL;
		}
	}

	public int refreshPoint(HttpSession session) {
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int point = 0;

		if (loginedMemberDto != null) {
			point = memberDao.selectNowPoint(loginedMemberDto.getM_id());
			loginedMemberDto.setPoint(point);

			session.setAttribute(Config.LOGINED_MEMBER_INFO, loginedMemberDto);
		}

		return point;
	}

	public MemberDto refreshMemberInfo(HttpSession session) {
		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		int point = 0;

		if (loginedMemberDto != null) {
			loginedMemberDto = memberDao.selectMember(loginedMemberDto.getM_id());
			
			point = memberDao.selectNowPoint(loginedMemberDto.getM_id());
			loginedMemberDto.setPoint(point);

			session.setAttribute(Config.LOGINED_MEMBER_INFO, loginedMemberDto);
		}
		return loginedMemberDto;
	}

}
