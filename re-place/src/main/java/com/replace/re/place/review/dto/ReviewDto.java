package com.replace.re.place.review.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long reviewId;
    private Long storeId;
    private Long userId;
    private String title;
    private String content;
    private Long viewCount;
    private Long likeCount;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    private Integer valid;

    public ReviewDto(Long storeId, Long userId, String title, String content) {
        // reviewId 사용 X
        this.storeId = storeId;
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}