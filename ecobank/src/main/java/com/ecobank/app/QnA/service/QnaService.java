package com.ecobank.app.QnA.service;

import java.util.List;


import com.ecobank.app.QnA.service.QnaVO;


public interface QnaService {
	
	//QNA 목록 조회
	List<QnaVO> qnaUserList();
	
	//QNA 등록
	int insertqnaInfo(QnaVO qnaVO);
	//QNA 단건 조회
	 QnaVO qnaSelectInfo(int qnaNo);
	
}
