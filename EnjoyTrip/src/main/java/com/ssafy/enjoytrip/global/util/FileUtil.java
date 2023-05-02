package com.ssafy.enjoytrip.global.util;

import com.ssafy.enjoytrip.global.dto.FileDto;
import com.ssafy.enjoytrip.global.mapper.FileMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
@AllArgsConstructor
public class FileUtil {
    private final String UPLOAD_PATH = "/upload";
    private ServletContext servletContext;
    private FileMapper fileMapper;
    public int upload(MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String realPath = servletContext.getRealPath(UPLOAD_PATH);
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            String saveFolder = realPath + File.separator + today;
            log.info("저장 폴더 : {}", saveFolder);
            File folder = new File(saveFolder);
            if (!folder.exists())
                folder.mkdirs();

            FileDto fileDto = new FileDto();
            String originalFileName = file.getOriginalFilename();
            if (!originalFileName.isEmpty()) {
                String type = originalFileName.substring(originalFileName.lastIndexOf('.'));
                String saveFileName = UUID.randomUUID().toString() + type;
                String size = "" + file.getSize();
                fileDto.setSaveFolder(today);
                fileDto.setOriginalFile(originalFileName);
                fileDto.setSaveFile(saveFileName);
                fileDto.setType(type);
                fileDto.setSize(size);
                log.info("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}, 확장자 : {}", file.getOriginalFilename(), saveFileName, type);
                file.transferTo(new File(folder, saveFileName));
            }

            log.info("fileDto : {}", fileDto);
            fileMapper.insertFileInfo(fileDto);

            return Integer.parseInt(fileDto.getFileId());
        }

        return 0;
    }
}
