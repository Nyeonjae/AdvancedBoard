package com.nyeonjae.advancedbbs.controllers;

import com.nyeonjae.advancedbbs.entities.ArticleEntity;
import com.nyeonjae.advancedbbs.entities.BoardEntity;
import com.nyeonjae.advancedbbs.results.article.DeleteArticleResult;
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

    @RequestMapping(value = "/modify", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getModify(@RequestParam(value = "index", required = false, defaultValue = "0")int index,
                                  @RequestParam(value = "password", required = false)String password){


        ModelAndView modelAndView = new ModelAndView();
        ArticleEntity article = this.articleService.getArticle(index); // 전달받은 Index로 게시글 가져옴
        if (article != null) { // 가져온 게시글이 존재하면 ( null이 아니면 )
            if (!article.getPassword().equals(password)) { // 가져온 게시글의 비밀번호와 전달받은 비밀번호가 일치하지 않을 경우
                article = null; // 지역변수 게시글을 null 로 지정
            }
        }
        if (article != null) { // 지역변수 게시글이 null이 아니면 (index 와 일치하는 게시글이 존재하고, 비밀번호도 일치하면 )
            BoardEntity board = this.boardService.getBoard(article.getBoardId()); // 게시글의 boardId로 게시판을 가져와서
            modelAndView.addObject("article", article); // 타임리프 HTML 로 게시글도 넘겨주고
            modelAndView.addObject("board", board); // 타임리프 HTML 로 게시판도 넘겨주고
        }

        modelAndView.setViewName("article/modify");
        return modelAndView;


    }

    @RequestMapping(value = "/read",method = RequestMethod.DELETE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteRead(@RequestParam(value = "index", required = false, defaultValue = "0")int index,
                             @RequestParam(value = "password", required = false)String password)
    {
        DeleteArticleResult result = this.articleService.deleteArticle(index, password);
        JSONObject response = new JSONObject();
        response.put("result", result.name().toLowerCase()); // "failure" / "failure_password" / "success"
        return response.toString();
    }


    @RequestMapping(value = "/read", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRead(@RequestParam(value = "index", required = false, defaultValue = "0") int index) {
        ArticleEntity article = this.articleService.getArticle(index);
        ModelAndView modelAndView = new ModelAndView();
        if ( article != null) {
            this.articleService.increaseArticleView(article);
            BoardEntity board = this.boardService.getBoard(article.getBoardId());
            modelAndView.addObject("board", board);
        }
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

