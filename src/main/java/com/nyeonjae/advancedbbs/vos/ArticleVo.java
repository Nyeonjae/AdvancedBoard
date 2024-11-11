package com.nyeonjae.advancedbbs.vos;

import com.nyeonjae.advancedbbs.entities.ArticleEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleVo extends ArticleEntity {
    private int commentCount;
}
