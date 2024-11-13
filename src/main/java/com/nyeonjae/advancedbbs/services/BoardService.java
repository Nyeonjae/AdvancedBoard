package com.nyeonjae.advancedbbs.services;

import com.nyeonjae.advancedbbs.entities.BoardEntity;
import com.nyeonjae.advancedbbs.mappers.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardMapper boardMapper;

    @Autowired
    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public BoardEntity getBoard(String id) {
        if (id == null || id.isEmpty() || id.length() > 10) {
            return null;
        }
        return this.boardMapper.selectBoardById(id);


        }
    public BoardEntity[] getBoards() {
        return this.boardMapper.selectBoards();
    }
}
