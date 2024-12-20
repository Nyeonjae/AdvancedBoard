package com.nyeonjae.advancedbbs.services;

import com.nyeonjae.advancedbbs.entities.CommentEntity;
import com.nyeonjae.advancedbbs.mappers.CommentMapper;
import com.nyeonjae.advancedbbs.results.article.DeleteCommentResult;
import com.nyeonjae.advancedbbs.results.article.ModifyCommentResult;
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

    public DeleteCommentResult deleteComment(int index, String password) {
        // 댓글 삭제를 위한 서비스 로직 ( 게시글 삭제와 로직이 같음 )
        // 1. 전달 받은 변수에 대한 정규화 실시 ( index가 1이상인지, password가 null은 아닌지, 길이는 옳은지 등등)
        // 2. 전달 받은 index를 index로 가지는 댓글이 DB에 존재하는지 SELECT 해오기.
        // 3. <2>가 없다면 (null) 실패로 반환.
        // 4. <2>가 없다면, 이의 'deletedAt' 값을 확인하고 이가 null이 아니라면 실패로 반환 ( 이미 삭제된것임 )
        // 5. <2> dml 'password'와 전달받은 'password' 가 일치하는지 확인. 불일치 한다면 실패로 반환.
        // 6. <2> dml 'deletedAt'을 현재 일시로 지정.
        // 7. <2>를 UPDATE 하고 결과 값이 0을 초과하는가의 여부에 따라 성공 및 실패 값 반환.
        if (index < 1 ||
                password == null || password.length() < 4 || password.length() > 50) {

            return DeleteCommentResult.FAILURE;
        }
        CommentEntity comment = this.commentMapper.selectCommentByIndex(index);
        if (comment == null) {
            return DeleteCommentResult.FAILURE;
        }
        if (comment.getDeletedAt() != null) {
            return DeleteCommentResult.FAILURE;
        }
        if (!comment.getPassword().equals(password)) {
            return DeleteCommentResult.FAILURE_PASSWORD;
        }
        comment.setDeletedAt(LocalDateTime.now());
        return this.commentMapper.updateComment(comment) > 0
                ? DeleteCommentResult.SUCCESS
                : DeleteCommentResult.FAILURE;
    }


    public CommentEntity[] getCommentsByArticleIndex(int articleIndex) {
        if (articleIndex < 1) {
            return null;
        }

        CommentEntity[] comments = this.commentMapper.selectCommentsByArticleIndex(articleIndex);
        for (CommentEntity comment : comments) {
            comment.setPassword(null);
        }
        return comments;
    }

    public ModifyCommentResult modifyComment(int index, String password, String content) {
        // 댓글의 내용을 수정하기 위한 서비스 로직을 작성합니다.

        // 1. 전달 받은 매개변수 index, password, content에 대해 정규화 합니다. (실패시 FAILURE 반환)
        if (content == null ||
                index < 1 ||
                password == null || password.length() < 4 || password.length() > 50 ||
        content.isEmpty() || content.length() > 100) {
            return ModifyCommentResult.FAILURE;
        }
        // 2. 전달 받은 index를 index로 가지는 CommentEntity 를 DB에서 가져옵니다.
        CommentEntity dbComment = this.commentMapper.selectCommentByIndex(index);
        // 3. <2> 가 없을 경우 (null) FAILRUE 를 반환합니다.
        if (dbComment == null) {
            return ModifyCommentResult.FAILURE;
        }
        // 4. <2>가 삭제된 댓글인 경우 (deletedAt 값이 null이 아닌 경우 ) FAILURE를 반환합니다.
        if (dbComment.getDeletedAt() != null) {
            return ModifyCommentResult.FAILURE;
        }
        // 5. <2>가 가지고 있는 password와 전달받은 password를 비교하여 다를 경우 FAILURE_PASSWORD를 반환합니다.
        if (!dbComment.getPassword().equals(password)) {
            return ModifyCommentResult.FAILURE_PASSWORD;
        }
        // 6. <2> 의 content 를 전달 받은 content로 지정, updatedAt을 현재 일시로 지정합니다.
        dbComment.setContent(content);
        dbComment.setUpdatedAt(LocalDateTime.now());
        // 7. <2> 를 DB상에서 UPDATE 하고 결과 값에 따라 SUCCESS 혹은 FAILURE를 반환합니다
        return this.commentMapper.updateComment(dbComment) > 0
                ? ModifyCommentResult.SUCCESS
                : ModifyCommentResult.FAILURE;



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
