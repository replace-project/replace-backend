package com.replace.re.place.review.dto;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReviewDto {
    private long storeId;
    private String reviewText;


    public ReviewDto() {

    }


    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }
}
