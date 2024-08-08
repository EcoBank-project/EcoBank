package com.ecobank.app.admin.service;

import java.util.Date;

import lombok.Data;

@Data
public class SnsDeclareVO {
					private int	 DECLARE_NO;		//sns 신고 번호
					private Date DECLAREAT;			//sns 신고 일자 
					private int	 CONFIRM_USER_NO;	//신고한 회원 번호
					private int	 DECLARE_CODE;		//sns 신고이유
					private int	 REPLY_NO;	 		//댓글 번호
					private int  FEED_NO;			//피드 번호

}
