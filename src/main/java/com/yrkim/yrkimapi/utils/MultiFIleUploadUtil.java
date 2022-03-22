package com.yrkim.yrkimapi.utils;


import com.yrkim.yrkimapi.exception.FileException;
import com.yrkim.yrkimapi.model.dto.BoardDto;
import com.yrkim.yrkimapi.model.dto.BoardFileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class MultiFIleUploadUtil {

    public String createFileName() {
        return UUID.randomUUID().toString();
    }

    public List<BoardFileDto> toBoardFile(MultipartFile[] multipartFiles, BoardDto boardDto) {
        // 반환을 할 파일 리스트
        List<BoardFileDto> fileList = new ArrayList<>();

        // 파일이 빈 것이 들어오면 빈 것을 반환
        if (multipartFiles.length <= 0) {
            return Collections.emptyList();
        }

        // 프로젝트 디렉터리 내의 저장을 위한 절대 경로 설정
        // 경로 구분자 File.separator 사용
        String absolutePath = new File("").getAbsolutePath() + File.separator;
        log.info("absolutePath : {}" , absolutePath);
        String path = "images" + File.separator + DateTimeUtil.nowDay() + File.separator;
        log.info("path : {}" , path);
        File file = new File(path);
        if(!file.exists()) {
            boolean isMkdir = file.mkdirs();
            if(!isMkdir) throw new FileException("now mkdir directory");
        }
        // 파일 이름을 업로드 한 날짜로 바꾸어서 저장 할 예정
        Arrays.asList(multipartFiles).stream().forEach(multipartFile -> {
            String oriFileName = multipartFile.getOriginalFilename();
            String fileContentType = multipartFile.getContentType();
            String fileExtension = oriFileName.substring(oriFileName.lastIndexOf(".") + 1);
            String newFileName = DateTimeUtil.nowDay()+createFileName();
            Long fileSize = multipartFile.getSize();

            log.info("oriFileName : {}" , oriFileName);
            log.info("fileContentType : {}" , fileContentType);
            log.info("newFileName : {}" , newFileName);
            log.info("fileExtension : {}" , fileExtension);
            log.info("fileSize : {}" , Long.toString(fileSize));
            log.info("newFilePath : {}" , absolutePath + path + newFileName + fileExtension);
            BoardFileDto boardFileDto = BoardFileDto
                    .builder()
                    .board(boardDto)
                    .originFileName(oriFileName)
                    .remoteFileName(newFileName)
                    .filePath(path)
                    .contentType(fileContentType)
                    .originalFileExtension(fileExtension)
                    .fileSize(fileSize)
                    .build();
            fileList.add(boardFileDto);
            try {
                multipartFile.transferTo(new File(absolutePath + path + newFileName + "." + fileExtension));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return fileList;
    }
}