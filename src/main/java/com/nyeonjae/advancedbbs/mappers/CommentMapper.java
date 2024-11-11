package com.nyeonjae.advancedbbs.mappers;

import com.nyeonjae.advancedbbs.entities.CommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.xml.stream.events.Comment;

@Mapper
public interface CommentMapper {
    int insertComment(CommentEntity comment);

    CommentEntity selectCommentByIndex(@Param("index") int index);

    CommentEntity[] selectCommentsByArticleIndex(@Param("articleIndex") int articleIndex);

   int updateComment(CommentEntity comment);
}

