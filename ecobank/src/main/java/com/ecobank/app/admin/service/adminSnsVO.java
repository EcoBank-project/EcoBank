package com.ecobank.app.admin.service;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class adminSnsVO {

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
	private Integer orderSns;			//정렬기준
	private String replyContent;   //댓글내용
	private Date replyCreateat; //댓글 작성일자
	private String  snsSituation;
	private int	 confirmUserNo;			 //신고한 회원 번호
    private LocalDateTime creaTeat;


	
	
}
