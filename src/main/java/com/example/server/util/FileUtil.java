package com.example.server.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class FileUtil {
    public static final SimpleDateFormat dfDate = new SimpleDateFormat(
            "yyyyMMdd");

    public static String getData() {
        Date date = new Date();

        return dfDate.format(date);
    }

    public static FileTime getFileCreateTime(File file) {

        try {
            return Files.readAttributes(Paths.get(file.getPath()), BasicFileAttributes.class).creationTime();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

}
