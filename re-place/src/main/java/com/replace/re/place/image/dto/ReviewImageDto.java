package com.replace.re.place.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImageDto {

    private Long reviewImageId;
    private Long reviewId;
    private Long imageId;

    public ReviewImageDto(Long reviewId, Long imageId){
        this.reviewId = reviewId;
        this.imageId = imageId;
    }
}
