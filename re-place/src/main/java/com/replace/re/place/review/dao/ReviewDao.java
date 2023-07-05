package com.replace.re.place.review.dao;

import com.replace.re.place.review.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewDao {


    private final JdbcTemplate jdbcTemplate;
    

    public class ReviewRowMapper implements RowMapper<ReviewDto> {
        @Override
        public ReviewDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReviewDto reviewDto = new ReviewDto(
                    rs.getLong("REVIEW_ID"),
                    rs.getLong("STORE_ID"),
                    rs.getLong("USER_ID"),
                    rs.getString("TITLE"),
                    rs.getString("CONTENT"),
                    rs.getLong("VIEW_COUNT"),
                    rs.getLong("LIKE_COUNT"),
                    rs.getTimestamp("CREATED_AT").toLocalDateTime(),
                    rs.getTimestamp("UPDATED_AT").toLocalDateTime(),
                    rs.getInt("VALID"));
            return reviewDto;
        }
    }

    // 모든 조회 메서드들은 valid 컬럼 값이 1인 행들만 조회한다.

    // 2-1. store_id(store 테이블 PK) 를 받아 그 값을 외래키로 가지는 review 테이블의 행 목록을 반환하는 메서드
    public List<ReviewDto> selectByStoreId(Long storeId) { // 메서드 리턴 타입이랑 result 타입 맞춰주기
        List<ReviewDto> result = jdbcTemplate.query("select * from review where STORE_ID = ? and VALID = 1", new ReviewRowMapper(), storeId); // <- 물음표에 달린 필드명 필요 ?
        return result; // 결괏값 반환
    }

    // 2-2. review_id(review 테이블 PK)를 받아 review 테이블에 그 값에 해당되는 pk를 가지는 단일 행을 리턴하는 메서드
    public ReviewDto selectByReviewId(Long reviewId) {
        // List<ReviewDto> results = jdbcTemplate.queryForObject("select * from REVIEW where REVIEWID = ?", new ReviewRowMapper(), reviewId); // 단일 행이라 List X
        ReviewDto result = jdbcTemplate.queryForObject("select * from review where REVIEW_ID = ? and VALID = 1", new ReviewRowMapper(), reviewId);
        return result;
    }

    // 2-3. 아래 컬럼 정보들을 받아 review 테이블에 새로운 행을 insert하고, auto_increment로 자동 생성된 pk 값(review_id 컬럼)을 리턴하는 메서드 작성
    public Long insertReview(ReviewDto reviewDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into review (STORE_ID, USER_ID, TITLE, CONTENT) values (?, ?, ?, ?)", new String[]{"REVIEW_ID"});
                pstmt.setLong(1, reviewDto.getStoreId());
                pstmt.setLong(2, reviewDto.getUserId());
                pstmt.setString(3, reviewDto.getTitle());
                pstmt.setString(4, reviewDto.getContent());

                return pstmt;
            }
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        return keyHolder.getKey().longValue(); // https://velog.io/@windowed/KeyHolder-JdbcTemplate-key-자동-생성
    }

    // 2-4. review_id(review 테이블 PK)를 받아 review 테이블에서 해당되는 행을 삭제하는 메서드 구현. (실제 delete 문을 사용하는 것이 아니고, valid 컬럼 값을 0으로 update)
    public Boolean deleteReview(Long reviewId) {
        return jdbcTemplate.update("update review set VALID = 0 where REVIEW_ID = ? and VALID = 1", reviewId) == 1;
    }

    // 2-5. pk, 제목, 내용을 받아 review 테이블에서 해당 pk에 해당되는 행의 정보(제목, 내용)을 update 하는 메서드 구현.
    public void updateReview(ReviewDto reviewDto) {
        // jdbcTemplate.update("insert into REVIEW (REVIEW_ID, TITLE, CONTENT) values (?, ?, ?)");
        jdbcTemplate.update("update review set TITLE = ?, CONTENT = ? where REVIEW_ID = ? and VALID = 1", reviewDto.getTitle(), reviewDto.getContent(), reviewDto.getReviewId()); // reviewId 지정해서 title이랑 content에 값 집어넣기
    }


    public Boolean checkReviewExist(Long reviewId){
        String query = "select if(exists(select * from review where review_id = ? and valid = 1), 1, 0)";

        return this.jdbcTemplate.queryForObject(query, Integer.class, reviewId) == 1;
    }

}
