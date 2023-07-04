package com.replace.re.place.store.dao;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreDao {

    //JdbcTemplate jdbcTemplate가 private final로 설정되어있는데.. 혹시 외부클래스(Dao1)에서 호출하는 방법..?

    //class Dao1 {
    // private JdbcTemplate jdbcTemplate;

    //Autowired를 이용해서
    //@Autowired
    //public Dao1(JdbcTemplate jdbcTemplate) {
    //this.JdbcTemplate = jdbcTemplate; 로 설정이 안되어서 일단 StoreDao 클래스에서 진행했음..
    //  }
    //}

    private long stored_id;
    private String name;
    private String latitude;
    private String longitude;
    private String address;


    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public StoreDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }




    //1-1 요구사항:위도, 경도, 주소,
    // 장소 이름 을 받아 store 테이블에 insert 하고, auto_increment로 자동 생성된 pk 값(store_id 컬럼)을 리턴하는 메서드 작성
    public long insert_Data(String name, String latitude, String longitude, String address) {

        String Insert_into_store_table = "INSERT INTO store (name,latitude,longitude,address) values(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(Insert_into_store_table, new String[]{"store_id"});

            pstmt.setString(1,name);
            pstmt.setString(2,latitude);
            pstmt.setString(3,longitude);
            pstmt.setString(4,address);

            return pstmt;


        },keyHolder);

        if(keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();

        }else {
            throw new RuntimeException("EE");
        }



    }

    //1-2 요구사항: 위도, 경도, 주소, 장소 이름을 받아 store 테이블에 같은 행이 있는지 여부를 리턴하는 메서드 구현

   public boolean checkIfExists(String latitude, String longitude, String address, String name) {



        String Check_query = "select count(*) from store where latitude = ? and longitude = ? and address = ? and name = ?";

        Integer count = jdbcTemplate.queryForObject(Check_query, new Object [] {latitude,longitude,address,name},
                Integer.class);

        return count!= null && count > 0 ;

   }

//1-3. pk, 위도, 경도, 주소, 장소 이름을 받아 store 테이블에 pk에 해당되는 행의 정보를
// 위도, 경도, 주소, 장소이름으로 update 하는 메서드 구현
   public int updateStore(int store_id, String latitude,
                          String longitude, String address, String name) {

        String SQL = "UPDATE store set latitude = ? , longitude = ? , address = ? , name = ? where store_id = ?";

        int update = jdbcTemplate.update(SQL,latitude,longitude,address,name,store_id);

        return update;

   }


   //1-4. 위도, 경도를 받아 store 테이블에서 같은 위, 경도를 가지는 행 목록을 반환하는 메서드 구현
   public List<Store> isEqualsColumn(String latitude, String longitude) {

        String Query_isEqualsColumn = "select * from store where latitude = ? and longitude = ?";

        return jdbcTemplate.query(Query_isEqualsColumn, new Object[]{latitude, longitude},
                new BeanPropertyRowMapper<>(Store.class) {
        });

    }


    //1-5. store_id(PK) 에 해당되는 숫자값을 받아 store 테이블에서 그 값을 pk로 하는 단일 행을 반환하는 메서드 구현

    public Store getStoreById(long stored_id) {
        String SQL = "SELECT * FROM store where sotre_id = ?";

        return jdbcTemplate.queryForObject(SQL, new Object[] {stored_id},new BeanPropertyRowMapper<>());
    }


    //1-6. store_id(PK)에 해당되는 숫자값을 받아 store 테이블에서 그 값을 pk로 하는 행을 삭제하는 메서드 구현
    public void pk_delete(long stored_id) {

        String SQL = "UPDATE store set valid = 0 where store_id = ?";

        jdbcTemplate.update(SQL,stored_id);

    }

    //1-7. 남서쪽 좌표, 북동쪽 좌표를 받아 store 테이블의 행들 중 이 좌표 범위 내에 존재하는 모든 행 목록을 리턴하는 메서드 구현

    public List<Store> get_coordinate (float South_latitude , float South_longitude,
                                       float North_latitude, float North_longitude) {

        String SQL = "SELECT * FROM store where latitude between ? and ? and longitude, between ? and ? ";


        return jdbcTemplate.query(SQL , new Object[]{South_latitude,South_longitude
                ,North_latitude,North_longitude},new BeanPropertyRowMapper<>());




    }

//    jdbcTemplate.query() 메서드는 SQL 쿼리를 실행하고,
//    BeanPropertyRowMapper를 사용하여 결과 row를 Store 객체에 매핑한 후, 이 객체들의 리스트를 반환합니다.


   }






