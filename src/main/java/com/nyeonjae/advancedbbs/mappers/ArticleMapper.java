package com.nyeonjae.advancedbbs.mappers;

import com.nyeonjae.advancedbbs.entities.ArticleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {
    int insertArticle(ArticleEntity article);

    ArticleEntity selectArticleByIndex(@Param("index") int index);

}


