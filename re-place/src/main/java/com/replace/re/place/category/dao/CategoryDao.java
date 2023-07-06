package com.replace.re.place.category.dao;

import com.replace.re.place.category.dto.CategoryDto;
import com.replace.re.place.category.dto.ReviewCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    class CategoryRowMapper implements RowMapper<CategoryDto>{

        @Override
        public CategoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            CategoryDto categoryDto = new CategoryDto(
                    rs.getLong("category_id"),
                    rs.getString("name")
            );
            return categoryDto;
        }
    }

    class ReviewCategoryRowMapper implements RowMapper<ReviewCategoryDto>{

        @Override
        public ReviewCategoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReviewCategoryDto reviewCategoryDto = new ReviewCategoryDto(
                    rs.getLong("category_id"),
                    rs.getLong("review_id")
            );

            return reviewCategoryDto;
        }
    }


    public Long insertCategory(String categoryName){
        String query = "insert into category(name) values(?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(query, new String[]{"category_id"});

                pstmt.setString(1, categoryName);
                return pstmt;
            }
        }, keyHolder);

        Number key = keyHolder.getKey();
        Long categoryId = key.longValue();

        return categoryId;
    }

    // Category 테이블에 인자로 주어진 문자열을 name으로 가지는 행이 있는지의 여부를 리턴.
    public Boolean checkCategoryExistByCategoryName(String categoryName){
        String query = "select if(exists(select * from category where name = ?), 1, 0)";

        return this.jdbcTemplate.queryForObject(query, Integer.class, categoryName) == 1;
    }


    // Category 테이블에 인자로 주어진 category_id를 가지는 행이 있는지의 여부를 리턴.
    public Boolean checkCategoryExist(Long categoryId){
        String query = "select if(exists(select * from category where category_id = ?), 1, 0)";

        return this.jdbcTemplate.queryForObject(query, Integer.class, categoryId) == 1;
    }


    // Category 테이블에 인자로 주어진 review_category_id를 가지는 행이 있는지의 여부를 리턴.
    public Boolean checkReviewCategoryExist(Long reviewCategoryId){
        String query = "select if(exists(select * from review_category where review_category_id = ?), 1, 0)";

        return this.jdbcTemplate.queryForObject(query, Integer.class, reviewCategoryId) == 1;
    }


    public Boolean checkReviewCategoryExistByReviewId(Long reviewId){
        String query ="select if(exists(select * from review_category where review_id = ?), 1, 0)";

        return this.jdbcTemplate.queryForObject(query, Integer.class, reviewId) == 1;
    }


    public List<CategoryDto> getAllCategory(){
        String query = "select * from category";

        return this.jdbcTemplate.query(query, new CategoryRowMapper());
    }


    // Category 테이블에 인자로 주어진 categoryId를 pk로 가지는 단일 행을 리턴.
    public CategoryDto getCategoryByCategoryId(Long categoryId){
        String query = "select * from category where category_id = ?";

        return this.jdbcTemplate.queryForObject(query, new CategoryRowMapper(), categoryId);
    }


    // Category 테이블에 인자로 주어진 categoryId를 pk로 가지는 단일 행을 리턴.
    public CategoryDto getCategoryByCategoryName(String categoryName){
        String query = "select * from category where name = ?";

        return this.jdbcTemplate.queryForObject(query, new CategoryRowMapper(), categoryName);
    }



    // Category 테이블에 인자로 주어진 categoryId를 pk로 가지는 단일 행을 삭제.
    public Boolean deleteCategory(Long categoryId){
        String query = "delete from category where category_id = ?";

        return this.jdbcTemplate.update(query, categoryId) == 1;
    }


    public Long insertReviewCategory(Long reviewId, Long categoryId){
        String query = "insert into review_category(review_id, category_id) values(?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(query, new String[]{"review_category_id"});
                pstmt.setLong(1, reviewId);
                pstmt.setLong(2, categoryId);

                return pstmt;
            }
        }, keyHolder);

        Number key = keyHolder.getKey();
        Long reviewCategoryId = key.longValue();

        return reviewCategoryId;
    }

    public Boolean checkReviewCategoryExistByCategoryId(Long categoryId){
        String query = "select if(exists(select * from review_category where category_id = ?),1 , 0)";

        return this.jdbcTemplate.queryForObject(query, Integer.class, categoryId) == 1;
    }



    public ReviewCategoryDto getReviewCategoryByReivewCategoryId(Long reviewCategoryId){
        String query = "select * from review_category where review_category_id = ?";

        return this.jdbcTemplate.queryForObject(query, new ReviewCategoryRowMapper(), reviewCategoryId);
    }


    public List<CategoryDto> getCategoriesByReviewId(Long reviewId){
        String query = "select * from category where category_id in " +
                "(select category_id from review_category where review_id = ?)";

        return this.jdbcTemplate.query(query, new CategoryRowMapper(), reviewId);
    }



    public Boolean deleteReviewCategoryByReviewId(Long reviewId){
        String query = "delete from review_category where review_id = ?";

        return this.jdbcTemplate.update(query, reviewId) != 0;
    }
}
