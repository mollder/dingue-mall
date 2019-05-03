package org.ingue.mall.pojo;

public enum Board {
    USER("유저게시판");

    private String boardName;

    Board(String boardName) {
        this.boardName = boardName;
    }
}
