package com.replace.re.place.category.service;

import com.replace.re.place.category.dao.CategoryDao;
import com.replace.re.place.category.dto.CategoryDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    private static List<String> categoryNames = new ArrayList<>();

    @BeforeAll
    static void initTest(){
        categoryNames.add("테스트1");
        categoryNames.add("테스트2");
        categoryNames.add("테스트3");
    }


    @Test
    void getAllCategoryTest() {
    }

    @Test
    void getCategoriesByReviewIdTest() {
//        categoryService.getCategoriesByReviewId()
    }

    @Test
    @DisplayName("CategoryService - 리뷰 카테고리 삽입 테스트")
    @Transactional
    void insertReviewCategoryTest() {

        Boolean isInserted = categoryService.insertReviewCategory(1L, categoryNames);
//        if(isInserted){
            List<CategoryDto> categoryDtos = categoryService.getCategoriesByReviewId(1L);
            assertEquals(categoryDtos.size(), categoryNames.size());
//        }
    }

    @Test
    @DisplayName("CategoryService - 리뷰 카테고리 삭제 테스트")
    @Transactional
    void deleteReviewCategoryTest() {
        categoryService.insertReviewCategory(1L, categoryNames);

        Boolean isDeleted = categoryService.deleteReviewCategory(1L);
//        if(isDeleted){
            List<CategoryDto> categoryDtos = categoryService.getCategoriesByReviewId(1L);
            assertEquals(0, categoryDtos.size());
//        }
    }
}