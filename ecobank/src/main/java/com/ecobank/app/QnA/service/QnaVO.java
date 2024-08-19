package com.ecobank.app.QnA.service;

import java.sql.Date;

import lombok.Data;

@Data
public class QnaVO {
	private int qnaNo;
	private String qnaContent;
	private String qnaTitle;
	private Date qnaCreateat;
	private int userNo;
}
