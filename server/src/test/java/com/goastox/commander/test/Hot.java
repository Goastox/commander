package com.goastox.commander.test;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Hot {

    @ExcelProperty("版权ID")
    private String copyrightId;
    @ExcelProperty("歌曲名")
    private String name;
    @ExcelProperty("歌手名")
    private String singer;
    @ExcelProperty("热度")
    private Integer hot;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"copyrightId\":\"")
                .append(copyrightId).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"singer\":\"")
                .append(singer).append('\"');
        sb.append(",\"hot\":")
                .append(hot);
        sb.append('}');
        return sb.toString();
    }
}
