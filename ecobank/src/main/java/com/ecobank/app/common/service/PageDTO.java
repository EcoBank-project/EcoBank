package com.ecobank.app.common.service;

import lombok.Data;

@Data
public class PageDTO { //화면 하단에 보이는 페이지 개수를 계산하기 위한 용도
	private int pageCount; 		//화면 하단에 출력할 페이지의 사이즈
	private int startPage; 		//페이지 시작 번호 
	private int endPage; 		//페이지 끝 번호
	private int realEnd; 		//한 블럭의 끝 페이지
	private boolean prev, next;	//이전, 다음 버튼 존재 유무
	private int total; 			//행 전체 개수
	private Criteria criteria; 	//현재페이지 번호(pageNum), 행 표시 수(amount), 검색 키워드(keyword), 검색 종류(type)
	//페이지 번호 계산에 필요한 Criteria 클래스의 멤버 변수들에 대한 정보를 가지는 변수
	private String keyword;		//검색
	
	public PageDTO() {
			
		}
	
	//생성자(클래스 호출 시 각 변수 값 초기화)
	public PageDTO(int pageCount, int total, Criteria criteria) {
		this.pageCount = pageCount;
		this.total = total;
		this.criteria = criteria;
		
		//페이지 끝 번호
		this.endPage = (int)(Math.ceil(criteria.getPageNum()*1.0/pageCount))*pageCount;
		this.startPage = endPage - (pageCount-1);
		
		//전체 마지막 페이지 번호
		realEnd = (int)(Math.ceil(total*1.0 / criteria.getAmount()));
		
		//페이지 끝 번호 유효성 체크
		if(endPage > realEnd) {
			endPage = realEnd == 0 ? 1 : realEnd;
		}
		
		//이전 버튼 값 초기화
		prev = startPage > 1;
		//다음 버튼 값 초기화
		next = endPage < realEnd;
		
		
	}
}
