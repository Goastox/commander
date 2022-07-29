package com.goastox.decision.lexer;

public class Token {

    private int typeInt;
    private String typeStr;
    private String code;
    private String error;

    public Token(String typeStr, String code) {
        this.typeStr = typeStr;
        this.code = code;
    }

    public int getTypeInt() {
        return typeInt;
    }

    public void setTypeInt(int typeInt) {
        this.typeInt = typeInt;
    }

    public String getTypeStr() {
        return typeStr;
    }
    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
