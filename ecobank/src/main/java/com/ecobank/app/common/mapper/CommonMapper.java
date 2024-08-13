package com.ecobank.app.common.mapper;

import java.util.List;

import com.ecobank.app.common.service.CodeVO;

public interface CommonMapper {
	public List<CodeVO> codeList(String codeType);
}
