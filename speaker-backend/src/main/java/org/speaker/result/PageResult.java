package org.speaker.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class PageResult implements Serializable {
    private long total;     //总记录数
    private List records;   //当前页数据集合
}
