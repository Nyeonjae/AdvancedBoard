package com.nyeonjae.advancedbbs.mappers;

import com.nyeonjae.advancedbbs.entities.ArticleEntity;
import com.nyeonjae.advancedbbs.vos.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {
    int insertArticle(ArticleEntity article);

    ArticleEntity selectArticleByIndex(@Param("index") int index);

    int selectArticleCountByBoardId(@Param("boardId") String boardId);

    int selectArticleCountBySearch(@Param("boardId") String boardId,
                                   @Param("filter") String filter,
                                   @Param("keyword") String keyword);

    ArticleVo[] selectArticlesByBoardId(@Param("boardId") String boardId,
                                        @Param("limitCount") int limitCount,        // 선택 갯수 ( 제한 갯수 )
                                        @Param("offsetCount") int offsetCount);     // 거를 갯수 ( 건너뛸 갯수 )


    ArticleVo[] selectArticlesBySearch(@Param("boardId") String boardId,
                                       @Param("filter") String filter,
                                       @Param("keyword") String keyword,
                                       @Param("limitCount") int limitCount,
                                       @Param("offsetCount") int offsetCount);

    int updateArticle(ArticleEntity article);

}


