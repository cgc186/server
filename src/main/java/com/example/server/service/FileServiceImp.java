package com.example.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.dao.FileMapper;
import com.example.server.entity.FileData;
import com.example.server.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.attribute.FileTime;
import java.util.UUID;

@Service
public class FileServiceImp implements FileService {
    @Autowired
    FileMapper mapper;

    String fileSavePath = "D:/";

    @Override
    public String upload(HttpServletRequest req, MultipartFile file) {
        if (file != null) {
            //文件名
            String originalFilename = file.getOriginalFilename();
            String substring = "";
            if (originalFilename != null) {
                substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            //文件大小
            long size = file.getSize();

            //接收到文件后将文件重命名（使用UUID）
            UUID uuid = UUID.randomUUID();

            String name = uuid.toString().replace("-", "");

            String newName = name + substring;

            //文件保存地址,目录格式yyyyMMdd
            String path = fileSavePath + FileUtil.getData();

            File dest = new File(path + "/" + newName);
            //判断文件父目录是否存在,不存在就创建
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
            }
            //保存文件
            try {
                file.transferTo(dest);
                //成功保存则将元数据保存入数据库
                FileData data = new FileData();
                //设置文件uuid
                data.setUuid(name);
                //设置文件大小
                data.setFileSize(size);

                //设置文件创建时间
                FileTime fileCreateTime = FileUtil.getFileCreateTime(dest);
                if (fileCreateTime != null) {
                    data.setCreateTime(fileCreateTime.toString());
                }
                //设置保存目录
                data.setSaveDirectory(path);

                if (originalFilename != null) {
                    //设置原始文件名
                    data.setOriginalFilename(originalFilename);
                    //设置文件类型
                    data.setFileType(FileUtil.getFileType(originalFilename));
                }

                //将文件原始数据插入数据库
                mapper.insert(data);
                //返回uuid
                return name;
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                return "出错";
            }
        }

        return "文件为空";
    }

    @Override
    public String getFile(HttpServletResponse response, String uuid) {
        FileData fileData = mapper.selectOne(
                new QueryWrapper<FileData>()
                        .eq("uuid", uuid));

        String filePath = fileData.getSaveDirectory();

        String fileType = fileData.getFileType();

        /**
         * 拼接文件路径
         */
        File file = new File(filePath + "/" + uuid + fileType);

        if (file.exists()) { //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            try {
                response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(uuid, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[1024];
            FileInputStream fis; //文件输入流
            BufferedInputStream bis;

            OutputStream os; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
                bis.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("------文件下载完成------ uuid:" + uuid);
        }
        return null;
    }

    @Override
    public FileData getFileData(String uuid) {
        return mapper.selectOne(
                new QueryWrapper<FileData>()
                        .eq("uuid", uuid));
    }
}
