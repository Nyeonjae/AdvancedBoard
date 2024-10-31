package com.nyeonjae.advancedbbs.controllers;

import com.nyeonjae.advancedbbs.entities.ArticleEntity;
import com.nyeonjae.advancedbbs.entities.BoardEntity;
import com.nyeonjae.advancedbbs.services.ArticleService;
import com.nyeonjae.advancedbbs.services.BoardService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final BoardService boardService;

    @Autowired
    public ArticleController(ArticleService articleService, BoardService boardService) {
        this.articleService = articleService;
        this.boardService = boardService;
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRead(@RequestParam(value = "index", required = false, defaultValue = "0") int index) {
        ArticleEntity article = this.articleService.getArticle(index);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("article", article);
        modelAndView.setViewName("article/read");
        return modelAndView;

    }

    @RequestMapping(value = "/write", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getWrite(
            @RequestParam(value = "boardId", required = false) String boardId)
    {
        BoardEntity board = this.boardService.getBoard(boardId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("board", board);
        modelAndView.setViewName("article/write");
        return modelAndView;
    }

    @RequestMapping(value = "/write", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postWrite(ArticleEntity article) {
        boolean result = this.articleService.write(article);
        JSONObject response = new JSONObject();
        response.put("result", result);
        if (result) {
            response.put("index", article.getIndex());
        }
        return response.toString();
    }
}

