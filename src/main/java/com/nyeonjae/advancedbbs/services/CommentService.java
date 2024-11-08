package com.nyeonjae.advancedbbs.services;

import com.nyeonjae.advancedbbs.entities.CommentEntity;
import com.nyeonjae.advancedbbs.mappers.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    private final CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }


    public CommentEntity[] getCommentsByArticleIndex(int articleIndex) {
        if (articleIndex < 1) {
            return null;
        }
        return this.commentMapper.selectCommentsByArticleIndex(articleIndex);
    }


    public boolean writeComment(CommentEntity comment) {
        if (comment == null ||
                comment.getArticleIndex() < 1 ||
                comment.getCommentIndex() != null && comment.getCommentIndex() < 1 ||
                comment.getNickname() == null || comment.getNickname().isEmpty() || comment.getNickname().length() > 10 ||
                comment.getPassword() == null || comment.getPassword().length() < 4 || comment.getPassword().length() > 50 ||
                comment.getContent() == null || comment.getContent().isEmpty() || comment.getContent().length() > 100) {
            return false;
        }
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(null);
        comment.setDeletedAt(null);
        return this.commentMapper.insertComment(comment) > 0;

    }

}
