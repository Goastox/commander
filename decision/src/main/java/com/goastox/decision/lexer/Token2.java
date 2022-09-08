package com.goastox.decision.lexer;

public interface Token2<T> {

    enum TokenType {
        String, Variable, Number, Char, Operator, Pattern, Delegate
    }


    TokenType getType();




}
