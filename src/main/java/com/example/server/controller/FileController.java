package com.example.server.controller;

import com.example.server.entity.FileData;
import com.example.server.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileService fileService;

    @RequestMapping("upload")
    @ResponseBody
    public String uploadFile(HttpServletRequest req, MultipartFile file) {
        return fileService.upload(req, file);
    }

    @RequestMapping("download")
    public String downloadFile(HttpServletResponse response, String uuid) {
        return fileService.getFile(response, uuid);
    }

    @RequestMapping("getFileData")
    @ResponseBody
    public FileData getFileData(String uuid) {
        return fileService.getFileData(uuid);
    }
}
