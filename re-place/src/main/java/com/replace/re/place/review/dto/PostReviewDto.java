package com.replace.re.place.review.dto;

import com.replace.re.place.category.dao.CategoryDao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostReviewDto {

    private ReviewDto reviewDto;
    private List<MultipartFile> multipartFiles;
    private List<String> categoryNames;
//    private StoreDto storeDto;
}
