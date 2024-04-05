package com.maru.chaekmaru.admin.util;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UploadFileService {
	
	public String upload(MultipartFile file) {
	      log.info("upload()");
	      
	      boolean result = false;
	      
	      // File 저장
	      String fileOriName = file.getOriginalFilename();	      
	      String uploadDir = "c:\\ChaekMaru\\upload\\chart";
	      	       
	      File saveFile = new File(uploadDir + "\\" + fileOriName);
	      if (!saveFile.exists())
	         saveFile.mkdirs();
	      
	      try {
	         
	         file.transferTo(saveFile);
	         result = true;
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	         
	      }
	      
	      if (result) {
	         log.info("FILE UPLOAD SUCCESS!!");
	         
	         return fileOriName;
	         
	      } else {
	         log.info("FILE UPLOAD FAIL!!");
	         
	         return null;
	         
	      }
	      
	   }

}
