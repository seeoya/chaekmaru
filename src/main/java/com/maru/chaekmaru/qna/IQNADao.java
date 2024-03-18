package com.maru.chaekmaru.qna;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IQNADao {

	public ArrayList<QNADto> selectMyQNAs(String m_id);
	
	public ArrayList<QNADto> selectSbData(String m_id);
	
	public QNADto selectQNA(int q_no);

	public int insertQ(QNADto qNADto);	

	public int updateA(QNADto qNADto);	

	public int deleteQNA(int q_no);

	public ArrayList<QNADto> selectQNAs();

	

	

}
