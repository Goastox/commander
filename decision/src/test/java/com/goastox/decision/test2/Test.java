package com.goastox.decision.test2;


import com.alibaba.fastjson.JSON;
import com.goastox.decision.lexer.Lexer;
import com.goastox.decision.lexer.Token;

import java.io.*;
import java.util.List;

public class Test {

    static int index = 0;
    static List<Token> tokens;

    static String token;

    public static void main(String[] args) throws IOException {
//        InputStream stream = new FileInputStream(new File("/Users/konghanghang/Documents/cust.csv"));
        InputStream stream = new FileInputStream(new File("/Users/konghanghang/commander/decision/src/main/java/com/goastox/decision/lexer/r.txt"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

        Lexer lexer = new Lexer(bufferedReader);
        lexer.lex();
        tokens = lexer.getTokens();
        tokens.stream().forEach(System.out::println);
        token = tokens.get(index).getCode();
        int expr = expr();
        System.out.println(expr);


    }

    public static void next(String var){
        if(!token.equals(var)){
            System.exit(-1);
        }
        token = tokens.get(++index).getCode();
    }

    public static int expr(){
        int term = term();
        return expr_tail(term);
    }

    public static int term(){
        int factor = factor();
        return term_tail(factor);
    }



    public static int factor(){
        int value = 0;
        if("(".equals(token)){
            next("(");
            value = expr();
            next(")");
            return value;
        }else {
            value = Integer.parseInt(token);
            next(value+"");
        }
        return value;
    }

    public static int term_tail(int var){
        if("*".equals(token)){
            next("*");
            int i = var * factor();
            return term_tail(i);
        } else if ("/".equals(token)) {
            next("/");
            int i = var / factor();
            return term_tail(i);
        }else
            return var;
    }


    public static int expr_tail(int var){
        if("+".equals(token)){
            next("+");
            int i = var + term();
            return expr_tail(i);
        }else if ("-".equals(token)){
            next("-");
            int i = var - term();
            return expr_tail(i);
        }else
            return var;
    }

//3*3*3
}
