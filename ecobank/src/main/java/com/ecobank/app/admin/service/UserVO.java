package com.ecobank.app.admin.service;

import java.util.Date;

import lombok.Data;


@Data
public class UserVO {
	
	private  int   userNo;    //회원번호
	private String useId;     //회원아이디
	private String password;  //비밀번호
	private String nickName;  //닉네임
	private  Date  creaTeat; //가입일
	private String tell;       //연락처 
	private String profileImg;  // 프로필 이미지
	private String resp;        //권한
	private String country;    //국가
	private String regiPath;    //가입경로
	private String userState;   //회원상태
	private String provide;		// google
	private String provideId;	// 구글 로그인 유저 고유 ID
	private String roleName; 
	private int cnt;
	private int n1;
	private int n2;
	private Date confirmCreateat;
	private int feedNo;
	private int replyNo;
	private int replySituation;
	private int R1;
	private int R2;
	


}
