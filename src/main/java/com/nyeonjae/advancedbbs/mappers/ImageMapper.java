package com.nyeonjae.advancedbbs.mappers;

import com.nyeonjae.advancedbbs.entities.ImageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.awt.*;

@Mapper
public interface ImageMapper {
    int insertImage(ImageEntity image);

    ImageEntity selectImageByIndex(@Param("index") int index);

}



