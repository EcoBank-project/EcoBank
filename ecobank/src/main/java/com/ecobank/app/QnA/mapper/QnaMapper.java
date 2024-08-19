package com.ecobank.app.QnA.mapper;

import java.util.List;

import com.ecobank.app.QnA.service.QnaVO;

public interface QnaMapper {
	
	//QNA 전체 조회
	List<QnaVO> qnaUserList();
	//QNA 등록
	int insertqnaInfo(QnaVO qnaVO);
	//QNA 단건 조회
	 QnaVO qnaSelectInfo(int qnaNo);
	 //qna삭제
	 int qnaDelete(int qnaVo);
}
