package com.nyeonjae.advancedbbs.controllers;

import com.nyeonjae.advancedbbs.entities.ArticleEntity;
import com.nyeonjae.advancedbbs.entities.BoardEntity;
import com.nyeonjae.advancedbbs.entities.ImageEntity;
import com.nyeonjae.advancedbbs.results.article.DeleteArticleResult;
import com.nyeonjae.advancedbbs.services.ArticleService;
import com.nyeonjae.advancedbbs.services.BoardService;
import jdk.jfr.ContentType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

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

    @RequestMapping(value ="/image", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@RequestParam(value = "index", required = false, defaultValue = "0") int index) {
        ImageEntity image = this.articleService.getImage(index);
        if (image == null) {
            return ResponseEntity.notFound().build(); // 응답 내용은 없고, 상태 코드는 404(Not Found)인 형태로 응답을 내보냄.
        }
        return ResponseEntity
                .ok() // 응답 상태 코드를 정상 ( 200 ) 으로 설정
                .contentLength(image.getData().length) // 응답 내용의 길이를 설정
                .contentType(MediaType.parseMediaType(image.getContentType())) // 응답 MIME 타입 설정( 이게 이미지인지 압축 파일인지 텍스트 파일인지 등등)
                .body(image.getData()); // 응답 내용 설정 ( 응답 내용 설정이 항상 마지막이여야 함 )
    }


    @RequestMapping(value = "/image", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postImage(@RequestParam(value = "upload") MultipartFile file) throws IOException {
        ImageEntity image = new ImageEntity();
        image.setData(file.getBytes());
        image.setContentType(file.getContentType());
        image.setName(file.getOriginalFilename());
        JSONObject response = new JSONObject();
        boolean result = this.articleService.uploadImage(image);
        if (result) {
            response.put("url", "/article/image?index=" + image.getIndex());
        }
        return response.toString();
    }


    @RequestMapping(value = "/modify", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getModify(@RequestParam(value = "index", required = false, defaultValue = "0")int index,
                                  @RequestParam(value = "password", required = false)String password){


        ModelAndView modelAndView = new ModelAndView();
        ArticleEntity article = this.articleService.getArticle(index); // 전달받은 Index로 게시글 가져옴
        if (article != null && article.getPassword().equals(password)) { // 가져온 게시글이 존재하고 비밀번호가 같으면
             BoardEntity board = this.boardService.getBoard(article.getBoardId()); // 게시글의 boardId로 게시판을 가져와서
             modelAndView.addObject("article", article); // 타임리프 HTML로 게시글도 넘겨주고
             modelAndView.addObject("board", board); // 타임리프 HTML로 게시판도 넘겨주고
             modelAndView.addObject("boards", this.boardService.getBoards());
        }
        modelAndView.setViewName("article/modify");
        return modelAndView;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchModify(@RequestParam(value = "oldPassword", required = false)String oldPassword, ArticleEntity article) {
        boolean result = this.articleService.modifyArticle(article, oldPassword);
        JSONObject response = new JSONObject();
        response.put("result", result);
        return response.toString();
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
            modelAndView.addObject("boards", this.boardService.getBoards());
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
        if (board != null) {
            modelAndView.addObject("boards", this.boardService.getBoards());
        }

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

