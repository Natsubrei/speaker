package org.speaker.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Post implements Serializable {
    private Long id;
    private Long userId;
    private String username;
    private String title;
    private String content;
    private String contentBrief;
    private LocalDateTime createTime;
}
