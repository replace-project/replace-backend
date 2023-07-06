package com.replace.re.place.store.service;
import com.replace.re.place.store.dao.Store;


import com.replace.re.place.review.dto.ReviewDto;
import com.replace.re.place.store.dao.StoreDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreDao storeDao;





    public List<Store> getStoresWithinCoordinates(String southLatitude, String southLongitude, String northLatitude, String northLongitude) {
        return storeDao.get_coordinate(southLatitude, southLongitude, northLatitude, northLongitude);
    }

    public List<ReviewDto> getReviewsWithinCoordinates(String southLatitude, String southLongitude, String northLatitude, String northLongitude) {
        List<Store> stores = getStoresWithinCoordinates(southLatitude, southLongitude, northLatitude, northLongitude);

        List<ReviewDto> reviews = new ArrayList<>();
        for (Store store : stores) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setStoreId(store.getStored_id());


            reviewDto.setReviewText("리뷰:  " + store.getStored_id());
            reviews.add(reviewDto);
        }

        return reviews;

    }

    //1-2를 호출하여 같은 행이 존재한다면, 그 행 정보를 1-8를 호출하여 리턴하고,
    // 만일 존재하지 않는다면 1-1을 호출하여 생성 후 리턴받은 pk값을 이용하여 1-5를 호출하여 리턴하는 서비스단 메서드 구현.


    public Store getOrCreateStore(String name, String latitude, String longitude, String address) {
        if (storeDao.checkIfExists(latitude, longitude, address, name)) {
            return storeDao.getStore(latitude, longitude, address, name);
        } else {
            long storeId = storeDao.insert_Data(name, latitude, longitude, address);
            return storeDao.getStoreById(storeId);
        }
    }

}


