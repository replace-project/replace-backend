package com.replace.re.place.review.dto;

import com.replace.re.place.category.dto.CategoryDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetDetailedReviewDto {
    private ReviewDto reviewDto;
    private List<String> imageUrls;
    private List<CategoryDto> categoryDtos;

    // storeDto
}
