package com.goastox.decision.lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Lexer {

    private static final String[] PUNCTUATIONS = {";", ".", "(", ")", ","};

    private static final String[] OPERATORS = {"+", "-", "*", "/", ">", "<", "==",
            "=", ">=", "<=", "|", "&", "~"};

    private static final String[] RESERVED_WORD = {"input","and","or","not","if","else","end","switch","case","default"};


    private final String ID = "ID";
    private final String NUMBER = "number";
    private final String NOTE = "note";
    private final String BLOCK_NOTE = "block_note";
    private final String RESERVED = "reserved_word";
    private final String OPERATOR = "operator";
    private final String PUNCTUATION = "punctuation";
    private final String OTHER = "other";

    private final BufferedReader stream;
    private List<Token> tokens = new LinkedList<>();

    public List<Token> getTokens() {
        return this.tokens;
    }

    public Lexer(BufferedReader stream) {
        this.stream = stream;
    }


    public void lex() throws IOException {
        int state = 0;
        int c = stream.read();
        Word word = new Word();
        while (stream.ready()){
            char c0 = (char) c;
            switch (state){
                case 0:
                    word = new Word();
                    /*if (c == 0x2D){//  - 负数 词法分析拆开，语法分析做特殊处理
                        state = 1;
                        word.add(c);
                        c = stream.read();
                    }
                    else*/ if (isNumber(c)){
                        state = 2;
                        word.add(c);
                        c = stream.read();
                    } else if (isLetter(c)) {
                        state = 4;
                        word.add(c);
                        c = stream.read();
                    } else if (c == 0x2F) {// /符号
                        state = 8;
                        word.add(c);
                        c = stream.read();
                    } else if (c == 0x20) {
                        state = 0;
                        c = stream.read();
                    } else {
                        state = 15;
                        word.add(c);
                        c = stream.read();
                    }
                    break;
                case 1:
                    if (isNumber(c)) {
                        state = 2;
                        word.add(c);
                        c = stream.read();
                    }else {
                        // 异常
                        state = 0;
                    }
                    break;
                case 2:
                    if (isNumber(c)){
                        state = 2;
                        word.add(c);
                        c = stream.read();
                    }else if (c ==0x2E){// .
                        state = 3;
                        word.add(c);
                        c = stream.read();
                    } else {
                        state = 11;
                    }
                    break;
                case 3:
                    if (isNumber(c)){
                        state = 10;
                        word.add(c);
                        c = stream.read();
                    } else {
                        //不是数字 非法字符
                        state = 0;
                    }
                    break;
                case 4:
                    if (isNumber(c) || isLetter(c) || c == 0x5F) {//支持下划线
                        state = 4;
                        word.add(c);
                        c = stream.read();
                    }else {
                        state = 12;
                    }
                    break;
                case 5:
                    /*  /    */
                    if (c == 0x2A) {// * 说明是注释块
                        state = 6;
                        word.add(c);
                        c = stream.read();
                    } else {
                        state = 5;
                        word.add(c);
                        c = stream.read();
                    }
                    break;
                case 6:
                    if(c == 0x2F){//  /
                        state = 13;
                        word.add(c);
                        c = stream.read();
                    }else {
                        state = 6;
                        word.add(c);
                        c = stream.read();
                    }
                    break;
                case 8:
                    if (c == 0x2F) { // /
                        state = 9;
                        word.add(c);
                        c = stream.read();
                    } else if (c == 0x2A) {// *
                        state = 5;
                        word.add(c);
                        c = stream.read();
                    } else {
                        // 异常
                        state = 0;
                    }
                    break;
                case 9:
                    if(c == 0x0A){//换行符
                        state = 14;
                        c = stream.read();
                    } else {
                        state = 9;
                        word.add(c);
                        c = stream.read();
                    }
                    break;
                case 10:
                    if (isNumber(c)){
                        state = 10;
                        word.add(c);
                        c = stream.read();
                    } else {
                        // TODO 考虑异常情况
                        state = 11;
                    }
                    break;
                case 11:
                    //判断是数字类型
                    tokens.add(new Token(NUMBER, word.toString()));
                    state = 0;
                    break;
                case 12:
                    //变量
                    // 关键字
                    if(isReservedWord(word.toString())){
                        tokens.add(new Token(RESERVED, word.toString()));
                    }else {
                        tokens.add(new Token(ID, word.toString()));
                    }
                    state = 0;
                    break;
                case 13:
                    // 注释块
//                    tokens.add(new Token(BLOCK_NOTE, word.toString()));
                    state = 0;
                    break;
                case 14:
                    // 一行注释
//                    tokens.add(new Token(NOTE, word.toString()));
                    state = 0;
                    break;
                case 15:
                    if(isOperator(word.toString())){
                        tokens.add(new Token(OPERATOR, word.toString()));
                    } else if (isPunctuations(word.toString())) {
                        tokens.add(new Token(PUNCTUATION, word.toString()));
                    } else {
                        tokens.add(new Token(OTHER, word.toString()));
                    }
                    state = 0;
                    break;
                    // = 赋值符号
                    // 标点符号
                    //other
            }
        }
    }

    private static boolean isNumber(int c){
        if(c >= 0x30 && c <= 0x39){// 48 <=c<= 57
            return true;
        }
        return false;
    }
    private static boolean isLetter(int c){
        if(c >=0x41 && c <= 0x5A){
            return true;
        } else if (c >= 0x61 && c <= 0x7A) {
            return true;
        } else if (c >= 0xff) {
            return true;
        }
        return false;
    }

    private static boolean isReservedWord(String word){
        for (String s : RESERVED_WORD) {
            if(word.equals(s)){
                return true;
            }
        }
        return false;
    }

    private static boolean isOperator(String word){
        for (String s : OPERATORS) {
            if(word.equals(s)){
                return true;
            }
        }
        return false;
    }

    private static boolean isPunctuations(String word){
        for (String s : PUNCTUATIONS) {
            if(word.equals(s)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        int a = 3*-2;
        System.out.println(a);

    }
}
