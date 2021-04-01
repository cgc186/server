package com.example.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("file_data")
public class FileData {
    @TableId(value = "id", type = IdType.AUTO)
    int id;
    @TableField(value = "uuid", exist = true)
    String uuid;
    @TableField(value = "fileSize", exist = true)
    Long fileSize;
    @TableField(value = "fileType", exist = true)
    String fileType;
    @TableField(value = "originalFilename", exist = true)
    String originalFilename;
    @TableField(value = "createTime", exist = true)
    String createTime;
    @TableField(value = "saveDirectory", exist = true)
    String saveDirectory;
}
