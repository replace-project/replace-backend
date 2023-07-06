package com.replace.re.place.store.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreDao {


    private long stored_id;
    private String name;
    private String latitude;
    private String longitude;
    private String address;



    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public StoreDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //1-1 요구사항:위도, 경도, 주소,
    // 장소 이름 을 받아 store 테이블에 insert 하고, auto_increment로 자동 생성된 pk 값(store_id 컬럼)을 리턴하는 메서드 작성
    public long insert_Data(String name, String latitude, String longitude, String address) {

        String Insert_into_store_table = "INSERT INTO store (name,latitude,longitude,address) values(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(Insert_into_store_table, new String[]{"store_id"});

            pstmt.setString(1, name);
            pstmt.setString(2, latitude);
            pstmt.setString(3, longitude);
            pstmt.setString(4, address);

            return pstmt;


        }, keyHolder);

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();

        } else {
            throw new RuntimeException("EE");
        }


    }

    //1-2 요구사항: 위도, 경도, 주소, 장소 이름을 받아 store 테이블에 같은 행이 있는지 여부를 리턴하는 메서드 구현

    public boolean checkIfExists(String latitude, String longitude, String address, String name) {


        String Check_query = "select count(*) from store where latitude = ? and longitude = ? and address = ? and name = ?";

        Integer count = jdbcTemplate.queryForObject(Check_query, new Object[]{latitude, longitude, address, name},
                Integer.class);

        return count != null && count > 0;

    }








    //1-3. pk, 위도, 경도, 주소, 장소 이름을 받아 store 테이블에 pk에 해당되는 행의 정보를
// 위도, 경도, 주소, 장소이름으로 update 하는 메서드 구현
    public int updateStore(int store_id, String latitude,
                           String longitude, String address, String name) {

        String SQL = "UPDATE store set latitude = ? , longitude = ? , address = ? , name = ? where store_id = ?";

        int update = jdbcTemplate.update(SQL, latitude, longitude, address, name, store_id);

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

        return jdbcTemplate.queryForObject(SQL, new Object[]{stored_id}, new BeanPropertyRowMapper<>());
    }


    //1-6. store_id(PK)에 해당되는 숫자값을 받아 store 테이블에서 그 값을 pk로 하는 행을 삭제하는 메서드 구현
    public void pk_delete(long stored_id) {

        String SQL = "UPDATE store set valid = 0 where store_id = ?";

        jdbcTemplate.update(SQL, stored_id);

    }

    //1-7. 남서쪽 좌표, 북동쪽 좌표를 받아 store 테이블의 행들 중 이 좌표 범위 내에 존재하는 모든 행 목록을 리턴하는 메서드 구현

    public List<Store> get_coordinate(String South_latitude, String South_longitude,
                                      String North_latitude, String North_longitude) {

        String SQL = "SELECT * FROM store where latitude between ? and ? and longitude, between ? and ? ";


        return jdbcTemplate.query(SQL, new Object[]{South_latitude, South_longitude
                , North_latitude, North_longitude}, new BeanPropertyRowMapper<>());


    }

//1-7 : 2
    // - 남서쪽 좌표, 북동쪽 좌표를 받아 그 좌표 범위 내 존재하는 모든 store들의 store_id를가지는 reviewDto목록을 리턴하는 **서비스단** 메서드 구현

    //이 부분은 ReviewService클래스에 구현했습니다.




    //1-8. 위도, 경도, 주소, 장소 이름을 받아 store 테이블에서 해당되는 단일 행을 반환하는 메서드 구현

    public Store getStore(String latitude, String longitude, String address, String name) {
        String SELECT_STORE_SQL = "SELECT * FROM store WHERE latitude = ? AND longitude = ? AND address = ? AND name = ?";

        RowMapper<Store> rowMapper = (rs, rowNum) -> {
            Store store = new Store();
            store.setStored_id(rs.getLong("store_id"));
            store.setLatitude(rs.getString("latitude"));
            store.setLongitude(rs.getString("longitude"));
            store.setAddress(rs.getString("address"));
            store.setName(rs.getString("name"));
            return store;
        };

        return jdbcTemplate.queryForObject(SELECT_STORE_SQL, rowMapper, latitude, longitude, address, name);
    }


    //1-2를 호출하여 같은 행이 존재한다면, 그 행 정보를 1-8를 호출하여 리턴하고,
    // 만일 존재하지 않는다면 1-1을 호출하여 생성 후 리턴받은 pk값을 이용하여 1-5를 호출하여 리턴하는 서비스단 메서드 구현.
    //해당 부분에 대해서는 StoreService클래스에 구현 하였습니다.


}


//    jdbcTemplate.query() 메서드는 SQL 쿼리를 실행하고,
//    BeanPropertyRowMapper를 사용하여 결과 row를 Store 객체에 매핑한 후, 이 객체들의 리스트를 반환합니다.









