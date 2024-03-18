package com.maru.chaekmaru.qna;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.config.Config;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class QNAService {

	@Autowired
	IQNADao qNADao;

	

	public ArrayList<QNADto> setMyQNAs(String m_id) {
		log.info("setMyQNAs()");
		
		return qNADao.selectMyQNAs(m_id);
	}

		
	public ArrayList<QNADto> getSbData(String m_id) {
		log.info("getSbData()");
		
		return qNADao.selectSbData(m_id);
		
	}
	
	
	public QNADto setQNA(int q_no) {
		log.info("setQNA()");
		
		return qNADao.selectQNA(q_no);
	}
	

	public int qWriteConfirm(QNADto qNADto) {
		log.info("qWriteConfirm()");
		
		int result = -1;
		result = qNADao.insertQ(qNADto);

		if (result > 0) {
			return Config.QNA_REGIST_SUCCESS;
		} else {
			return Config.QNA_REGIST_FAIL;
		}
	}
	
	
	public void aWriteConfirm(QNADto qNADto) {
		log.info("aWriteConfirm()");
		
		int result = -1;
		result = qNADao.updateA(qNADto);

		if (result > 0) {
			log.info(Config.QNA_ANSWER_REGIST_SUCCESS);
		} else {
			log.info(Config.QNA_ANSWER_REGIST_FAIL);
		}
	}
	

	public int deleteConfirm(int q_no) {
		log.info("deleteConfirm()");
		
		int result = -1;

		result = qNADao.deleteQNA(q_no);

		if (result > 0) {
			return Config.QNA_DELETE_SUCCESS;
		} else {
			return Config.QNA_DELETE_FAIL;
		}
	}


	public ArrayList<QNADto> setQNAs() {
		log.info("setQNAs()");
		
		return qNADao.selectQNAs();
		
	}

	
}
