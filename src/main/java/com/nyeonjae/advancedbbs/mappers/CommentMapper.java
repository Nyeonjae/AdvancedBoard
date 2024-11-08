package com.nyeonjae.advancedbbs.mappers;

import com.nyeonjae.advancedbbs.entities.CommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {
    int insertComment(CommentEntity comment);

    CommentEntity[] selectCommentsByArticleIndex(@Param("articleIndex") int articleIndex);

}
