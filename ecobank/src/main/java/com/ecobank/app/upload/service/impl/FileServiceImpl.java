package com.ecobank.app.upload.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.ecobank.app.upload.mapper.FileMapper;
import com.ecobank.app.upload.service.FileService;
import com.ecobank.app.upload.service.FileVO;

@Controller
public class FileServiceImpl implements FileService{
	@Value("${file.upload.path}")
	private String uploadPath;
	
	private FileMapper fileMapper;
	
	@Autowired
	FileServiceImpl(FileMapper fileMapper){
		this.fileMapper = fileMapper;
	}

	//등록
		@Override
		public int insertFile(MultipartFile[] images, String fileCode, int codeNo) {
			int result = 0;
			FileVO fileVO = new FileVO();
			for(MultipartFile image : images) {
				//1)원래 파일이름
				String fileName = image.getOriginalFilename();
				 //파일 형식
				//String fileType = image.getContentType();
				//고유한 식별자로 이미지 저장해서 클라이언트가 업로드했을때 파일이름이 겹치지 않도록 하는거
				UUID uuid = UUID.randomUUID();
				String uniqueFileName = uuid + "_" + fileName;
				
				//2)실제로 저장할 경로를 생성 : 서버의 업로드 경로 + 파일이름
				String saveName = uploadPath + File.separator + uniqueFileName; //""가 /와 같아
				
				Path savePath = Paths.get(saveName); //여기에 경로 담았음
				
				//파일의 정보를 가져와서 boardVO에 파일의 이름을 넣어줌
				fileVO.setFileName(uniqueFileName);	//파일 이름
				fileVO.setFilePath(saveName); 		//파일 경로
				//fileVO.setFiletype(fileType); 		//파일 타입
				fileVO.setFileCode(fileCode);		//파일 분류 코드
				fileVO.setFileCodeNo(codeNo);		//파일 분류 번호
				
				result = fileMapper.insertFileInfo(fileVO);
				//3)*파일 작성(파일 업로드)
				try {
					image.transferTo(savePath); //*실제 경로 지정 /Path는 경로/transferTo=햇살
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			
			return result == 1 ? fileVO.getFileNo() : -1;
		}
	
	//수정
	@Override
	public Map<String, Object> updateFile(FileVO fileVO) {
		// TODO Auto-generated method stub
		return null;
	}

}
