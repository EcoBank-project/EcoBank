package com.ecobank.app.QnA.mapper;

import java.util.List;

import com.ecobank.app.QnA.service.QnaVO;

public interface QnaMapper {
	
	//QNA 목록 조회
		List<QnaVO> qnaUserList();
		//QNA 등록
		int insertqnaInfo(QnaVO qnaVO);
		//QNA 단건 조회
		 QnaVO qnaSelectInfo(int qnaNo);
		 //QNA  삭제
		 void deleteQna(int qnaNo);

		// QNA 답글 조회
		 
		// 답글 리스트 조회
		List<QnaVO> qnaReplyList(int qnaNo);
		//QnA 수정
		int updateQnaInfo(QnaVO qnaVo);

}