package com.ecobank.app.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.common.mapper.CommonMapper;

@Service
public class CommonService {
	@Autowired
	CommonMapper commonMapper;
	public List<CodeVO>codeList(String codeType) {
		return commonMapper.codeList(codeType);
	};
}
