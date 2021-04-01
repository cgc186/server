package com.example.server.service;

import com.example.server.entity.FileData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileService {
    String upload(HttpServletRequest req, MultipartFile file);

    String getFile(HttpServletResponse response,String uuid);

    FileData getFileData(String uuid);
}
