package com.ecobank.app.upload.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
		public int insertFile(MultipartFile[] images, String fileCode, int fileCodeNo) {
			int result = 0;
			FileVO fileVO = new FileVO();

			for(MultipartFile image : images) {
				if(image != null && image.getSize() > 0) {
					//1)원래 파일이름
					String fileName = image.getOriginalFilename(); 
					
					 //파일 형식
					//String fileType = image.getContentType();
					String folderPath = makeFolder();
					//고유한 식별자로 이미지 저장해서 클라이언트가 업로드했을때 파일이름이 겹치지 않도록 하는거
					UUID uuid = UUID.randomUUID();
					String uniqueFileName = folderPath + "/" + uuid + "_" + fileName;
					
					//2)실제로 저장할 경로를 생성 : 서버의 업로드 경로 + 파일이름
					
					String saveName = uploadPath + "/" + uniqueFileName; //""가 /와 같아
					
					Path savePath = Paths.get(saveName); //여기에 경로 담았음
					//파일의 정보를 가져와서 boardVO에 파일의 이름을 넣어줌
					//System.out.println("파일"+saveName);
					fileVO.setFileName(fileName);			//파일 이름
					fileVO.setFilePath(uniqueFileName); 	//파일 경로
					//fileVO.setFiletype(fileType); 		//파일 타입
					fileVO.setFileCode(fileCode);			//파일 분류 코드
					fileVO.setFileCodeNo(fileCodeNo);		//게시글번호
					
					result = fileMapper.insertFileInfo(fileVO);
					
					//3)*파일 작성(파일 업로드)
					try {
						image.transferTo(savePath); //*실제 경로 지정 /Path는 경로/transferTo/이미지 저장
					}catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
			return result == 1 ? fileVO.getFileNo() : -1;
		}
		
		private String makeFolder() {
			String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			// LocalDate를 문자열로 포멧
			//String folderPath = str.replace("/", File.separator);
			String folderPath = str.replace("/", File.separator);
	        File uploadPathFoler = new File(uploadPath, folderPath);
			//File uploadPathFoler = new File(str);
			// File newFile= new File(dir,"파일명");
			if (uploadPathFoler.exists() == false) {
				uploadPathFoler.mkdirs();
				// 만약 uploadPathFolder가 존재하지않는다면 makeDirectory하라는 의미입니다.
				// mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우에는 생성이 불가능한 함수
				// mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우에는 상위 디렉토리까지 모두 생성하는 함수
			}
			return str;
		}
		
		//삭제
		@Override
		public int deleteFile(int feedNo) {
			return fileMapper.deleteFileInfo(feedNo);
		}

		@Override
		public List<FileVO> selectFileInfo(int feedNo) {
			return fileMapper.selectSnsFileInfo(feedNo);
		}
		
		//나의 인증 내역 파일 - 이름은 같지만 다른 매개값을 가짐(메소드 오버라이딩)
		@Override
		public List<FileVO> selectFileInfo(int userNo, int challNo, String fileCode) {
			FileVO fileVO = new FileVO();
			fileVO.setUserNo(userNo);
			fileVO.setChallNo(challNo);
			fileVO.setFileCode(fileCode);
			//System.out.println(fileCode);
			return fileMapper.selectConfirmFileInfo(fileVO);
		}

		//다른 참가자 인증 내역 파일
		@Override
		public List<FileVO> selectFileOtherInfo(int challNo) {
			List<FileVO> list = fileMapper.selectOtherConfirm(challNo);
			return list;
		}

		//나의 인증 내역 상세 파일
		@Override
		public List<FileVO> selectGetMyInfo(int confirmNo) {
			return fileMapper.getMyConfirmFile(confirmNo);
		}

		//인증 파일 삭제
		@Override
		public int confirmFileDelete(int confirmNo) {
			return fileMapper.deleteConfirmFileInfo(confirmNo);
		}
		
}
