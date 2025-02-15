package org.speaker.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PostVO implements Serializable {
    private Long id;
    private Long userId;
    private String username;
    private String title;
    private String contentBrief;
    private LocalDateTime createTime;
}
