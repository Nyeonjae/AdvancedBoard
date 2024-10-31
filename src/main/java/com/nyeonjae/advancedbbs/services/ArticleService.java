package com.nyeonjae.advancedbbs.services;

import com.nyeonjae.advancedbbs.entities.ArticleEntity;
import com.nyeonjae.advancedbbs.mappers.ArticleMapper;
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

    public ArticleEntity getArticle(int index) {
        if (index < 1 ) {
            return null;
        }
        return this.articleMapper.selectArticleByIndex(index);
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






