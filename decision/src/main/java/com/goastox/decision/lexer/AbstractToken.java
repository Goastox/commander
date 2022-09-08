package com.goastox.decision.lexer;

public class AbstractToken {

    private final int line;

    private final int index;

    public AbstractToken(int line, int index) {
        this.line = line;
        this.index = index;
    }
}
