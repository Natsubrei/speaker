package org.speaker.pojo.dto;

import lombok.Data;

@Data
public class PostDTO {
    private Long userId;
    private String username;
    private String title;
    private String content;
}
