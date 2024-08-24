package com.ecobank.app.sns.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.ecobank.app.common.service.Criteria;

import lombok.Data;

@Data
public class SnsVO extends Criteria{
	

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
	private String fileName;		//파일이름
	private String filePath;		//파일이름
	private int fileCodeNo;			//파일코드번호
	private String codeId;			//신고코드
	private String snsdeclare;		//신고목록
	private int declareNo;			//신고번호
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date declareat ;			//신고날짜
	private String declareCode;			//신고코드
	private Integer replyNo;			//댓글번호
	private Integer confirmUserNo;		//신고이유
	private Integer orderSns;			//정렬기준
	private Integer followYn;			//팔로우여부
	private Integer snslikeYn;			//좋아요여부
	private Integer countFeed;			//피드갯수
	private Integer countFollowing;		//팔로잉 수
	private Integer countFollower;		//팔로워 수
	private Integer blockUserNo;		//차단한 회원 번호
	private Integer followerId;			//팔로워 아이디
	private Integer followingId;		//팔로잉 아이디
	private Integer snsBlockNo;			//차단 번호
	private Integer fileCount;			//파일 갯수
	
	
	
}
