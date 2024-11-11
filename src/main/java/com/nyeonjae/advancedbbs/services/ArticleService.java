package com.nyeonjae.advancedbbs.services;

import com.nyeonjae.advancedbbs.entities.ArticleEntity;
import com.nyeonjae.advancedbbs.entities.ImageEntity;
import com.nyeonjae.advancedbbs.mappers.ArticleMapper;
import com.nyeonjae.advancedbbs.mappers.ImageMapper;
import com.nyeonjae.advancedbbs.results.article.DeleteArticleResult;
import com.nyeonjae.advancedbbs.vos.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
//@RequiredArgsConstructor - lombok 의존성 주입이랑 같음 ( 생성자 만든거 )
public class ArticleService {
    private final ArticleMapper articleMapper;
    private final ImageMapper imageMapper;

    @Autowired
    public ArticleService(ArticleMapper articleMapper, ImageMapper imageMapper) {
        this.articleMapper = articleMapper;
        this.imageMapper = imageMapper;
    }

    public DeleteArticleResult deleteArticle(int index, String password) {
        if (index < 1 ||
                password == null || password.length() < 4 || password.length() > 50) {

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

    public ArticleVo[] getArticleByBoardId(String boardId) {
        if (boardId == null) {
            return null;
        }
        return this.articleMapper.selectArticlesByBoardId(boardId);
    }

    public ArticleEntity getArticle(int index) {
        if (index < 1) {
            return null;
        }
        return this.articleMapper.selectArticleByIndex(index);
    }

    public ImageEntity getImage(int index) {
        if (index < 1) {
            return null;
        }
        return this.imageMapper.selectImageByIndex(index);
    }

    public boolean increaseArticleView(ArticleEntity article) {

        if (article == null) {
            return false;
        }
        article.setView(article.getView() + 1);
        return this.articleMapper.updateArticle(article) > 0;
    }

    public boolean modifyArticle(ArticleEntity article, String oldPassword) {
            //                                  ^ 클라이언트가 준 것
        if (article == null ||
                article.getIndex() < 1 ||
                article.getNickname() == null || article.getNickname().length() < 2 || article.getNickname().length() > 10 ||
                article.getPassword() == null || article.getPassword().length() < 4 || article.getPassword().length() > 50 ||
                article.getTitle() == null || article.getTitle().isEmpty() || article.getTitle().length() > 100 ||
                article.getContent() == null || article.getContent().isEmpty() || article.getContent().length() > 16_777_215 ||
                oldPassword == null || oldPassword.length() < 4 || oldPassword.length() > 50) {
            return false;
        }
        ArticleEntity dbArticle = this.articleMapper.selectArticleByIndex(article.getIndex());
         //             ^ 데이터베이스에서 가지고 온것
        if (dbArticle == null || dbArticle.getDeletedAt() != null) { // 레코드가 존재하지 않거나, 이미 삭제된 게시글일 경우 ( 삭제 일시 값이 있을 경우 )
            return false;
        }
        if (!dbArticle.getPassword().equals(oldPassword)) {  // 데이터베이스상에 존재하는 게시글의 비밀번호와, 클라이언트가 전달해준 비밀번호가 다르면
            return false;
        }
//        article.setNickname(dbArticle.getNickname()); 이거 보단 아래 방식이 더 낫다

        dbArticle.setNickname(article.getNickname()); // 데이터베이스에서 가지고 온 게시글의 닉네임을 클라이언트가 전달해준 닉네임으로 지정한다.
        dbArticle.setPassword(article.getPassword()); // 이하 생략
        dbArticle.setTitle(article.getTitle());
        dbArticle.setContent(article.getContent());
        dbArticle.setUpdatedAt(LocalDateTime.now()); // 데이터베이스에서 가지고 온 게시글의 수정 일시를 현재 일시로 지정한다.
        // 이렇게 데이터베이스에서 가지고 온 객체의 set 메서드를 호출하여 수정이 필요한 필드의 값만 골라서 수정하는 것이 안전.
        // ( 나머지 필드의 불변을 보장, 가령 index, board_id, view 등등...)
        return this.articleMapper.updateArticle(dbArticle) > 0;
    }

    public boolean uploadImage(ImageEntity image) {
        if (image == null ||
            image.getData() == null ||
            image.getData().length == 0 ||
            image.getContentType() == null || image.getContentType().isEmpty() ||
            image.getName() == null || image.getName().isEmpty()) {
            return false;
        }
        image.setCreatedAt(LocalDateTime.now());
        return this.imageMapper.insertImage(image) > 0;
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






