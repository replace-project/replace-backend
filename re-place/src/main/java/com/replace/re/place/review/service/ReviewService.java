package com.replace.re.place.review.service;


import com.replace.re.place.review.dto.ReviewDto;
import com.replace.re.place.store.dao.Store;
import com.replace.re.place.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {


    private final StoreService storeService;



    //1-7 : 2
    // - 남서쪽 좌표, 북동쪽 좌표를 받아 그 좌표 범위 내 존재하는 모든 store들의 store_id를가지는 reviewDto목록을 리턴하는 **서비스단** 메서드 구현


    public List<ReviewDto> getReviewWithinCoordinate(String southLatitude, String southLongitude,
                                                     String northLatitude, String northLongitude) {

        List<Store> stores = storeService.getStoresWithinCoordinates(southLatitude, southLongitude, northLatitude, northLongitude);          ;

        List<ReviewDto> reviews = new ArrayList<>();


        for(Store store : stores ) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setStoreId(store.getStored_id());

            reviewDto.setReviewText("리뷰: " + store.getStored_id());
            reviews.add(reviewDto);


        }





        return reviews;

    }



}
