package com.ecobank.app.challenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecobank.app.challenge.mapper.ChallMapper;
import com.ecobank.app.challenge.service.ChallService;

public class ChallServiceImpl implements ChallService{
	@Autowired
	ChallMapper challMapper;
	
	
}
