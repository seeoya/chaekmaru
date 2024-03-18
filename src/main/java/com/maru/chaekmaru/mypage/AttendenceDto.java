package com.maru.chaekmaru.mypage;

import lombok.Data;

@Data
public class AttendenceDto {
	private String m_id;
	private int ac_attend_date;
	private String ac_reg_date;
	
	public AttendenceDto() {
	}
	
	public AttendenceDto(String ac_reg_date) {
		this.ac_reg_date = ac_reg_date;
	}
}
