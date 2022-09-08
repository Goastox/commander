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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"typeInt\":")
                .append(typeInt);
        sb.append(",\"typeStr\":\"")
                .append(typeStr).append('\"');
        sb.append(",\"code\":\"")
                .append(code).append('\"');
        sb.append(",\"error\":\"")
                .append(error).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
