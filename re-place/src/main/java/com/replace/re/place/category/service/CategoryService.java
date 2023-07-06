package com.replace.re.place.category.service;

import com.replace.re.place.category.dao.CategoryDao;
import com.replace.re.place.category.dto.CategoryDto;
import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.category.CategoryNotCreatedException;
import com.replace.re.place.global.exception.category.ReviewCategoryNotCreatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

import static com.replace.re.place.global.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDao categoryDao;


    // 모든 카테고리 목록 조회
    public List<CategoryDto> getAllCategory(){
        return categoryDao.getAllCategory();
    }


    // 어느 review에 속하는 카테고리 목록 조회
    public List<CategoryDto> getCategoriesByReviewId(Long reviewId){
        return categoryDao.getCategoriesByReviewId(reviewId);
    }


    // reviewId를 가지는 review에 소속되는 다수의 카테고리 등록
    public Boolean insertReviewCategory(Long reviewId, List<String> categoryNames){
        // reviewId에 대한 검증 필요. 추가 예

        Iterator<String> it = categoryNames.iterator();

        Long categoryId;
        while(it.hasNext()){
            String categoryName = it.next();

            if(categoryDao.checkCategoryExistByCategoryName(categoryName)){    //같은 이름의 카테고리가 존재할 경우
                CategoryDto categoryDto = categoryDao.getCategoryByCategoryName(categoryName);
                categoryId = categoryDto.getCategoryId();

            }else{    //같은 이름의 카테고리가 존재하지 않을 경우, 새롭게 생성

                // category 테이블에 행 삽입.
                categoryId = categoryDao.insertCategory(categoryName);
                Boolean isCategoryCreated = categoryDao.checkCategoryExist(categoryId);

                if(!isCategoryCreated){
                    throw new CategoryNotCreatedException(CATEGORY_NOT_CREATED);
                }
            }

            // review_category 테이블에 행 삽입.
            Long reviewCategoryId = categoryDao.insertReviewCategory(reviewId, categoryId);

            Boolean isReviewCategoryCreated = categoryDao.checkReviewCategoryExist(reviewCategoryId);
            if(!isReviewCategoryCreated){
                throw new ReviewCategoryNotCreatedException(REVIEW_CATEGORY_NOT_CREATED);
            }
        }

        // 로직 추가 예정
        return true;
    }


    // reviewId를 가지는 review의 카테고리 전부 삭제.
    public Boolean deleteReviewCategory(Long reviewId){

        List<CategoryDto> categoryDtos = categoryDao.getCategoriesByReviewId(reviewId);

        Boolean isDeleted = categoryDao.deleteReviewCategoryByReviewId(reviewId);
        if(isDeleted){
            Iterator<CategoryDto> it = categoryDtos.iterator();
            while(it.hasNext()){
                CategoryDto categoryDto = it.next();
                Long categoryId = categoryDto.getCategoryId();

                if(categoryDao.checkReviewCategoryExistByCategoryId(categoryId)){    // 삭제한 리뷰 내에서만 쓰이는 카테고리인 경우
                    categoryDao.deleteCategory(categoryId);
                }
            }
        }

        // 로직 추가 예정
        return true;
    }

}
