package com.maru.chaekmaru.book;

import lombok.Data;

@Data
public class ListPageDto {

	private String type;
	private String className;
	private int pageNum;

	public ListPageDto() {
	}

	public ListPageDto(String type, String className, int pageNum) {
		this.type = type;
		this.className = className;
		this.pageNum = pageNum;
	}

}
