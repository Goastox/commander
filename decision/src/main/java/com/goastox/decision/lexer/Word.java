package com.goastox.decision.lexer;

public final class Word {

    private static final int MAX_SIZE = 0x40;

    private char[] value;

    private int size;

    public Word() {
        this.value = new char[MAX_SIZE];
        this.size = 0;
    }

    public Word add(int c){
        if(size < MAX_SIZE){
            value[size++] = (char) c;
        }
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(value).substring(0, size);
    }
}
