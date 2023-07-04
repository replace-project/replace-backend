package com.replace.re.place.category.dao;

import com.replace.re.place.category.dto.CategoryDto;
import com.replace.re.place.category.dto.ReviewCategoryDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryDaoTest {

    @Autowired
    private CategoryDao categoryDao;


    @Test
    @DisplayName("CategoryDao - 조회 테스트")
    public void getCategoryByCategoryIdTest(){
        CategoryDto categoryDto = categoryDao.getCategoryByCategoryId(1L);

        assertEquals("음식", categoryDto.getCategoryName());
    }

    @Test
    @DisplayName("CategoryDao - Category 테이블 삽입 테스트")
    @Transactional
    public void insertCategoryTest(){
        Long categoryId = categoryDao.insertCategory("캠핑");

        CategoryDto categoryDto = categoryDao.getCategoryByCategoryName("캠핑");

        assertEquals(categoryId, categoryDto.getCategoryId());
    }


    @Test
    @DisplayName("CategoryDao.checkCategoryExist 테스트 - 1")
    public void checkCategoryExistTest(){

        Boolean isExist = categoryDao.checkCategoryExistByCategoryName("없는문자열");
        assertEquals(false, isExist);
    }


    @Test
    @DisplayName("CategoryDao.checkCategoryExist 테스트 - 2")
    public void checkCategoryExistTest2(){

        Boolean isExist = categoryDao.checkCategoryExistByCategoryName("음식");
        assertNotEquals(false, isExist);
    }

    @Test
    @DisplayName("CategoryDao.insertReviewCategory 테스트")
    @Transactional
    public void insertReviewCategoryTest(){
        Long categoryId = categoryDao.insertCategory("테스트");

        Long reviewCategoryId = categoryDao.insertReviewCategory(1L, categoryId);

        ReviewCategoryDto reviewCategoryDto = categoryDao.getReviewCategoryByReivewCategoryId(reviewCategoryId);

        assertEquals(reviewCategoryDto.getCategoryId(), categoryId);
    }
}