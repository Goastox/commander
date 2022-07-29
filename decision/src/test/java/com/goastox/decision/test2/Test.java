package com.goastox.decision.test2;


import com.alibaba.fastjson.JSON;
import com.goastox.decision.lexer.Lexer;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class Test {
    public static void main(String[] args) throws IOException {
        InputStream stream = new FileInputStream(new File("/Users/konghanghang/Documents/cust.csv"));
//        InputStream stream = new FileInputStream(new File("/Users/konghanghang/commander/decision/src/test/java/com/goastox/decision/test2/r.txt"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line = "";
        HashMap<String, Integer> map = new HashMap<>();
        while ((line = bufferedReader.readLine()) != null){
            if(map.get(line) == null){
                map.put(line, 1);
            }else{
                Integer integer = map.get(line);
                map.put(line, integer+1);
            }
        }
        map.forEach((k,v) ->{
            if(v > 1){
                System.out.println(k + "----"  +v);
            }
        });
//        Lexer lexer = new Lexer(bufferedReader);
//        lexer.lex();
//        lexer.getTokens().stream().forEach(x->{
//            System.out.println(JSON.toJSONString(x));
//
//
//        });


    }
}
