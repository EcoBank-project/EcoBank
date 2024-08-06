package com.ecobank.app.sns.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SnsVO {

	private Integer feedNo;			//피드번호
	private String profileImg;      //회원 이미지
	private String nickname;      	//회원 별명
	private String feedContent;		//피드내용
	private Integer userNo;			//회원번호
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date feedCreateAt;		//피드 작성 일자
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date feedUpdateAt;		//피드 수정 일자
	private String feedState;		//피드 상태
	private String hashtag;			//해시태그
	private Integer countLike;		//좋아요갯수
	private Integer countReply;		//댓글갯수
}
