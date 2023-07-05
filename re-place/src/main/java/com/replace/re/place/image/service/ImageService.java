package com.replace.re.place.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.replace.re.place.image.dao.ImageDao;
import com.replace.re.place.image.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageDao imageDao;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /*
    public List<String> getImage(Long ImageId){
        ArrayList<String> images = new ArrayList<>();
        images.add("https://via.placeholder.com/300x200.png?text=Sample+Image");

        return images;
    }

    public List<ImageDto> getReviewImagesByReviewId(Long reviewId){
        return imageDao.getReviewImagesByReviewId(reviewId);
    }
     */


    // 이미지 개수, 크기 등의 제한은 컨트롤러 또는 클라이언트 단에서 처리해야할 것.
//    public Boolean insertReviewImage(Long reviewId, List<String> imageUrls){
//
//        // 리뷰가 존재하는지에 대한 검증이 필요할 것.
//
//
//        for(String imageUrl : imageUrls){
//
//            Long imageId = imageDao.insertImage(imageUrl);
//
//            Long reviewImageId = imageDao.insertReivewImage(reviewId, imageId);
//        }
//
//        return true;
//    }


    // 이미지 개수, 크기 등의 제한은 컨트롤러 또는 클라이언트 단에서 처리해야할 것.
    public Boolean insertReviewImage(Long reviewId, List<MultipartFile> multipartFiles) throws IOException {

        // 리뷰가 존재하는지에 대한 검증이 필요할 것.

        for(MultipartFile multipartFile: multipartFiles) {
            String originalFilename = multipartFile.getOriginalFilename();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);


//            String imageUrl = amazonS3.getUrl(bucket, originalFilename).toString();

            // 데이터베이스에 기록
            Long imageId = imageDao.insertImage(originalFilename);
            Long reviewImageId = imageDao.insertReivewImage(reviewId, imageId);
        }

        return true;
    }


    public Boolean deleteReviewImage(Long reviewId){
        if(imageDao.checkReviewImageExist(reviewId)){
            List<ImageDto> reviewImageDtos = imageDao.getReviewImagesByReviewId(reviewId);

            imageDao.deleteReviewImageByReviewId(reviewId);

            Iterator<ImageDto> it = reviewImageDtos.iterator();
            while(it.hasNext()){
                ImageDto imageDto = it.next();

                //s3에서 삭제
                amazonS3.deleteObject(bucket, imageDto.getFilename());

                imageDao.deleteImageByImageId(imageDto.getImageId());
            }
        }

        // 검증 기능 추가 예정.
        return true;
    }
}
