package com.ecobank.app.sns.service;

import java.util.Date;

import lombok.Data;

@Data
public class SnsVO {

	private Integer feedNo;			//피드번호
	private String feedContent;		//피드내용
	private Integer userNo;			//회원번호
	private Date feedCreateAt;		//피드 작성 일자
	private Date feedUpdateAt;		//피드 수정 일자
	private String feedstate;		//피드 상태
	private String hashtag;			//해시태그
}
