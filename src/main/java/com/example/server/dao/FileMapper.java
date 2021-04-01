package com.example.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.entity.FileData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<FileData> {
}
