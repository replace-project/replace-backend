package com.replace.re.place.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCategoryDto {
    private Long reviewCategoryId;
    private Long categoryId;
    private Long reviewId;

    public ReviewCategoryDto(Long categoryId, Long reviewId) {
        this.categoryId = categoryId;
        this.reviewId = reviewId;
    }
}
