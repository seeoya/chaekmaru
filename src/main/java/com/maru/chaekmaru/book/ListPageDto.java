package com.maru.chaekmaru.book;

import lombok.Data;

@Data
public class ListPageDto {

	private String type;
	private String className;
	private int pageNum;
	private String pageText;

	public ListPageDto() {
	}

	public ListPageDto(String type, String className, int pageNum) {
		this.type = type;
		this.className = className;
		this.pageNum = pageNum;

		switch (type) {
		case "start":
			this.pageText = "&lt;&lt;";
			break;
		case "prev":
			this.pageText = "&lt;";
			break;
		case "num":
			this.pageText = Integer.toString(pageNum);
			break;
		case "next":
			this.pageText = "&gt;";
			break;
		case "end":
			this.pageText = "&gt;&gt;";
			break;

		default:
			break;
		}
	}

}
