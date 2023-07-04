package com.replace.re.place.image.service;

import com.replace.re.place.image.dao.ImageDao;
import com.replace.re.place.image.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageDao imageDao;

    /*
    이미지 저장은 AWS s3를 사용하기로 되어 있지만, 아직 S3 버킷을 생성하지 않아 임시로 이미지를 반환하는 코드를 구현.
     */
    public List<String> getImage(Long ImageId){
        ArrayList<String> images = new ArrayList<>();
        images.add("https://via.placeholder.com/300x200.png?text=Sample+Image");

        return images;
    }

    public List<ImageDto> getReviewImagesByReviewId(Long reviewId){
        return imageDao.getReviewImagesByReviewId(reviewId);
    }


    // 이미지 개수, 크기 등의 제한은 컨트롤러 또는 클라이언트 단에서 처리해야할 것.
    public Boolean insertReviewImage(Long reviewId, List<String> imageUrls){

        // 리뷰가 존재하는지에 대한 검증이 필요할 것.


        for(String imageUrl : imageUrls){

            Long imageId = imageDao.insertImage(imageUrl);

            Long reviewImageId = imageDao.insertReivewImage(reviewId, imageId);
        }

        return true;
    }


    public void deleteReviewImage(Long reviewId){
        if(imageDao.checkReviewImageExist(reviewId)){
            List<ImageDto> reviewImageDtos = imageDao.getReviewImagesByReviewId(reviewId);

            imageDao.deleteReviewImageByReviewId(reviewId);

            Iterator<ImageDto> it = reviewImageDtos.iterator();
            while(it.hasNext()){
                ImageDto imageDto = it.next();
                imageDao.deleteImageByImageId(imageDto.getImageId());
            }
        }
    }
}
