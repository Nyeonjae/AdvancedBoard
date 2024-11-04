package com.nyeonjae.advancedbbs.services;

import com.nyeonjae.advancedbbs.entities.ArticleEntity;
import com.nyeonjae.advancedbbs.mappers.ArticleMapper;
import com.nyeonjae.advancedbbs.results.article.DeleteArticleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
//@RequiredArgsConstructor - lombok 의존성 주입이랑 같음 ( 생성자 만든거 )
public class ArticleService {
    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public DeleteArticleResult deleteArticle(int index, String password) {
        if (index < 1 ||
            password ==null || password.length() < 4 || password.length() > 50 ) {

            return DeleteArticleResult.FAILURE;
        }
        ArticleEntity article = this.articleMapper.selectArticleByIndex(index);
        if (article == null) { // 클라이언트가 전달해준 index로 SELECT를 했더니 없더라 ( null )
            return DeleteArticleResult.FAILURE;
        }

        if (article.getDeletedAt() != null) {  // 게시글이 있기는 한데 ( != null ) , 이미 삭제된 게시글 이더라 ( deletedAt 이 null 이 아님 = 삭제 일시 값이 있음 )
            return DeleteArticleResult.FAILURE;
        }

        if (!article.getPassword().equals(password)) { // 게시글 작성당시 설정한 비밀번호와 니가 준 비밀번호가 불일치함
            return DeleteArticleResult.FAILURE_PASSWORD;
        }
        // article != null >>> 게시글이 DB 상에 존재를 한다.
        // article.getDeletedAt() == null >>> 게시글의 삭제 일시가 없다 ( 이전에 삭제된 적이 없다 )
        // article.getPassword().equals(password) >>> 게시글의 비밀번호화 니가 준 비밀번호가 일치
        article.setDeletedAt(LocalDateTime.now());
        return this.articleMapper.updateArticle(article) > 0
                ? DeleteArticleResult.SUCCESS
                : DeleteArticleResult.FAILURE;
    }




    public ArticleEntity getArticle(int index) {
        if (index < 1 ) {
            return null;
        }
        return this.articleMapper.selectArticleByIndex(index);
    }

    public boolean increaseArticleView(ArticleEntity article) {

        if (article == null) {
            return false;
        }
        article.setView(article.getView() + 1);
        return this.articleMapper.updateArticle(article) > 0;
    }

    public boolean write(ArticleEntity article) {
        if (article == null ||
                article.getBoardId() == null ||
                article.getNickname() == null || article.getNickname().length() < 2 || article.getNickname().length() > 10 ||
                article.getPassword() == null || article.getPassword().length() < 4 || article.getPassword().length() > 50 ||
                article.getTitle() == null || article.getTitle().isEmpty() || article.getTitle().length() > 100 ||
                article.getContent() == null || article.getContent().isEmpty() || article.getContent().length() > 16_777_215) {
                return false;
        }
        article.setView(0);
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(null);
        article.setDeletedAt(null);
        int affectedRows = this.articleMapper.insertArticle(article);
        return affectedRows > 0;
    }
}






