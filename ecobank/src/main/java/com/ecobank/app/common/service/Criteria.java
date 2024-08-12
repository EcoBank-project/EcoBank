package com.ecobank.app.common.service;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Data
public class Criteria { 	//공통으로 사용할수있는 하나의 클래스로 처리하기 위해
	private int pageNum; 	//현재 페이지의 번호
	private int amount; 	//페이지당 항목 수/페이지당 출력할 데이터 개수
	private String type; 	//검색 키워드
	private String keyword; //검색 유형 ex) 전체, 제목, 작성자 등
	
	//생성자로 무조건 1번 실행
	//기본 페이지를 1페이지 5개씩 보여준다는 의미
	public Criteria() {
		this(1, 5);
		
	}

	//매개변수로 들어오는 값을 이용하여 조정할 수 있음
	public Criteria(int pageNum, int amount) {
		super();
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	//UriComponentsBuilder를 이용하여 링크 생성
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
			.queryParam("pageNum", pageNum)
			.queryParam("amount", amount);
		
		return builder.toUriString();
		
	}
	
	//get으로 시작해야만 mybatis에서 찾을 수 있음
	public String[] getTypeArr() { 
		return type == null ? new String[] {} : type.split("");
	}
}
