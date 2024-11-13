package com.nyeonjae.advancedbbs.controllers;

import com.nyeonjae.advancedbbs.entities.BoardEntity;
import com.nyeonjae.advancedbbs.services.ArticleService;
import com.nyeonjae.advancedbbs.services.BoardService;
import com.nyeonjae.advancedbbs.vos.ArticleVo;
import com.nyeonjae.advancedbbs.vos.PageVo;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
    private final ArticleService articleService;
    private final BoardService boardService;

    @Autowired
    public BoardController(ArticleService articleService, BoardService boardService) {
        this.articleService = articleService;

        this.boardService = boardService;
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getList(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "keyword", required = false) String keyword) {
        System.out.println(page);
        BoardEntity board = this.boardService.getBoard(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("board", board);
        if (board != null) {

            if (filter == null || keyword == null) {
                Pair<PageVo, ArticleVo[]> pair = this.articleService.getArticleByBoardId(id, page);
                modelAndView.addObject("pageVo", pair.getLeft());
                modelAndView.addObject("articles", pair.getRight());
            }
            else {
                Pair<PageVo, ArticleVo[]> pair = this.articleService.searchArticles(id, page, filter, keyword);
                modelAndView.addObject("pageVo", pair.getLeft());
                modelAndView.addObject("articles", pair.getRight());
                modelAndView.addObject("filter", filter);
                modelAndView.addObject("keyword", keyword);
            }
            modelAndView.addObject("boards", this.boardService.getBoards());

        }
        modelAndView.setViewName("board/list");
        return modelAndView;
    }
}




