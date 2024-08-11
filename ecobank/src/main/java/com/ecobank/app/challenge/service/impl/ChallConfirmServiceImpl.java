package com.ecobank.app.challenge.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.challenge.mapper.ChallConfirmMapper;
import com.ecobank.app.challenge.service.ChallConfirmService;
import com.ecobank.app.challenge.service.ChallConfirmVO;
import com.ecobank.app.challenge.service.MyConfirmDTO;

@Service
public class ChallConfirmServiceImpl implements ChallConfirmService{
	private ChallConfirmMapper challConfirmMapper;
	
	@Autowired
	public ChallConfirmServiceImpl(ChallConfirmMapper challConfirmMapper) {
		this.challConfirmMapper = challConfirmMapper;
	}

	@Override
	public List<ChallConfirmVO> confirmList(ChallConfirmVO chalConfirmVO) {
		return challConfirmMapper.selectConfirmAll(chalConfirmVO);
	}

	@Override
	public MyConfirmDTO myConfirm(int userNo, int challNo) {
		MyConfirmDTO dto = challConfirmMapper.myConfirm(userNo, challNo);
		
		//남은 인증 횟수 구하기-오늘 했는지(카운트 그대로) or 오늘 인증 안했어(카운트 +1)
		//매퍼에서 인증횟수를 가져와
		int cnt = challConfirmMapper.getConfirmCnt(challNo);
		//오늘날짜구하기
		LocalDate today = LocalDate.now();  // 오늘 날짜를 LocalDate로 얻음
		boolean isTodayPresent = false; //현재 날짜가 인증날짜랑 다를때
		List<Date> dates = myCalendar(userNo, challNo); //인증날짜가져와
		for(Date date : dates) { //인증날짜 for문 돌려
			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            if(localDate.equals(today)) { //인증날짜랑 오늘날짜 비교했을때
                isTodayPresent = true; //오늘날짜가 맞다면 멈춰 -> 그대로
                break;
            }
		}
		if(!isTodayPresent) { //오늘 날짜가 인증날짜랑 다르면 cnt에 +1해줘
			cnt += 1;
		}
		dto.setRemainCount(cnt);
		//System.out.println(isTodayPresent);
		return dto;
	}

	@Override
	public List<Date> myCalendar(int userNo, int challNo) {
		return challConfirmMapper.getConfirmDate(userNo, challNo);
	}
}
