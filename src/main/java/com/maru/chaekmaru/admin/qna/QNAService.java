package com.maru.chaekmaru.admin.qna;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.config.Config;

@Service
public class QNAService {

	@Autowired
	IQNADao qNADao;

		
	public int getSbNo(String m_id) {
	
		return qNADao.selectSbNos(m_id);
		
	}
	
	
	public QNADto setQNA(int q_no) {

		return qNADao.selectQNA(q_no);
	}
	

	public int qWriteConfirm(QNADto qNADto) {
		int result = -1;
		result = qNADao.insertQ(qNADto);

		if (result > 0) {
			return Config.REVIEW_WRITE_SUCCESS;
		} else {
			return Config.REVIEW_WRITE_FAIL;
		}
	}
	
	
	public int aWriteConfirm(QNADto qNADto) {
		int result = -1;
		result = qNADao.updateA(qNADto);

		if (result > 0) {
			return Config.REVIEW_WRITE_SUCCESS;
		} else {
			return Config.REVIEW_WRITE_FAIL;
		}
	}
	

		public int modifyConfirm(QNADto qNADto) {
		int result = -1;

		result = qNADao.modifyQNA(qNADto);

		if (result > 0) {
			return Config.REVIEW_MODIFY_SUCCESS;
		} else {
			return Config.REVIEW_MODIFY_FAIL;
		}
	}

	public int deleteConfirm(int q_no) {
		int result = -1;

		result = qNADao.deleteQNA(q_no);

		if (result > 0) {
			return Config.REVIEW_DELETE_SUCCESS;
		} else {
			return Config.REVIEW_DELETE_FAIL;
		}
	}

	public ArrayList<QNADto> setMyQNAs(String m_id) {
		return qNADao.selectMyQNAs(m_id);
	}


	
}
