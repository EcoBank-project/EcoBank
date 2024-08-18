package com.ecobank.app.challenge.service;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class LikeDTO {
	private int totalCnt;	//전체 좋아요 갯수
	private int likeStatus;	//좋아요 등록, 삭제 상태
}
