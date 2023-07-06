package com.replace.re.place.category.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {
    private Long categoryId;
    private String categoryName;

    public CategoryDto(String categoryName) {
        this.categoryName = categoryName;
    }
}
