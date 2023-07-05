package com.replace.re.place.image.dao;

import com.replace.re.place.image.dto.ImageDto;
import lombok.AllArgsConstructor;
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
public class ImageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Autowired
//    public ImageDao(DataSource dataSource){
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }


    class ImageRowMapper implements RowMapper<ImageDto>{

        @Override
        public ImageDto mapRow(ResultSet rs, int rowNum) throws SQLException {

            ImageDto imageDto = new ImageDto(
                    rs.getLong("image_id"),
                    rs.getString("filename"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
            return imageDto;
        }
    }


    // 어느 한 리뷰에 속하는 이미지 목록을 반환하는 메서드
    public List<ImageDto> getReviewImagesByReviewId(Long reviewId){
        String query = "select * from image where image_id in (select image_id from review_image where review_id = ?)";

        return this.jdbcTemplate.query(query, new ImageRowMapper(), reviewId);
    }

    // Image 테이블에서 pk인 image_id로 행을 조회.
    public ImageDto getImageByImageId(Long imageId){
        String query = "select * from image where image_id = ?";

        return this.jdbcTemplate.queryForObject(query, new ImageRowMapper(), imageId);
    }


    // Image 테이블에 새로운 행 insert.
    public Long insertImage(String filename){
        String query = "insert into image(filename) values(?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(query, new String[]{"image_id"});
                pstmt.setString(1, filename);

                return pstmt;
            }
        }, keyHolder);

        Number key = keyHolder.getKey();
        Long imageId = key.longValue();

        return imageId;
    }

    // Image 테이블에서 pk인 image_id로 행 삭제.
    public void deleteImageByImageId(Long imageId){
        String query = "delete from image where image_id = ?";

        this.jdbcTemplate.update(query, imageId);
    }

    // Review_Image 테이블에서 review_id를 가지고 해당되는 행들의 image_id를 찾아 Image 테이블에서 삭제.
    public void deleteImageByReviewId(Long reviewId){
        String query = "delete from image where image_id in (select image_id from review_image where review_id = ?)";

        this.jdbcTemplate.update(query, reviewId);
    }

    // Review_Image 테이블에 행 insert
    public Long insertReivewImage(Long reviewId, Long imageId){
        String query = "insert into review_image(review_id, image_id) values(?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(query, new String[]{"review_image_id"});
                pstmt.setLong(1, reviewId);
                pstmt.setLong(2, imageId);

                return pstmt;
            }
        }, keyHolder);

        Number key = keyHolder.getKey();
        Long reviewImageId = key.longValue();

        return reviewImageId;
    }

    // Review_Image테이블에서 pk인 review_image_id로 행을 식별하여 삭제.
    public void deleteReviewImageByReviewImageId(Long reviewImageId){
        String query = "delete from review_image where review_image_id = ?";

        this.jdbcTemplate.update(query, reviewImageId);
    }


    // Review_Image테이블에서 fk인 review_id로 행을 식별하여 삭제.
    public void deleteReviewImageByReviewId(Long reviewId){
        String query = "delete from review_image where review_id = ?";

        this.jdbcTemplate.update(query, reviewId);
    }

    // Review Image 테이블에서 fk인 review_id를 가지는 행이 존재하는지 여부를 리턴.
    public Boolean checkReviewImageExist(Long reviewId){

        String query = "select if(exists(select * from review_image where review_id = ?), 1, 0)";

        return this.jdbcTemplate.queryForObject(query, Integer.class, reviewId) == 1;
    }

    // Image 테이블에서 pk인 image_id를 가지는 행이 존재하는지 여부를 리턴.
    public Boolean checkImageExist(Long imageId){
        String query = "select if(exists(select * from image where image_id = ?), 1, 0)";

        return this.jdbcTemplate.queryForObject(query, Integer.class, imageId) == 1;
    }
}
