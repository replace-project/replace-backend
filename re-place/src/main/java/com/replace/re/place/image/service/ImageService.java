package com.replace.re.place.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.image.ImageNotCreatedException;
import com.replace.re.place.global.exception.image.ImageNotDeletedException;
import com.replace.re.place.global.exception.image.ImageNotFoundException;
import com.replace.re.place.global.exception.image.ReviewImageNotCreatedException;
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

import static com.replace.re.place.global.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageDao imageDao;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


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
            Boolean isImageCreated = imageDao.checkImageExist(imageId);
            if(!isImageCreated){
                throw new ImageNotCreatedException(IMAGE_NOT_CREATED);
            }


            Long reviewImageId = imageDao.insertReivewImage(reviewId, imageId);
            Boolean isReviewImageCreated = imageDao.checkReviewImageExist(reviewImageId);
            if(!isReviewImageCreated){
                throw new ReviewImageNotCreatedException(REVIEW_IMAGE_NOT_CREATED);
            }
        }

        return true;
    }


    public Boolean deleteReviewImage(Long reviewId){

        Boolean isReviewImageExist = imageDao.checkReviewImageExist(reviewId);

        //리뷰에 이미지가 한 개도 없을 수 있기 때문에, 리뷰 이미지가 없더라도 예외발생 X.
        if(isReviewImageExist){
            List<ImageDto> reviewImageDtos = imageDao.getReviewImagesByReviewId(reviewId);

            imageDao.deleteReviewImageByReviewId(reviewId);

            Iterator<ImageDto> it = reviewImageDtos.iterator();
            while(it.hasNext()){
                ImageDto imageDto = it.next();
                Long imageId = imageDto.getImageId();
                String filename = imageDto.getFilename();

                //s3에서 삭제
                amazonS3.deleteObject(bucket, filename);

                Boolean isImageExist = imageDao.checkImageExist(imageId);
                if(isImageExist) {

                    imageDao.deleteImageByImageId(imageId);

                    Boolean isImageDeleted = !imageDao.checkImageExist(imageId);
                    if(!isImageDeleted){
                        throw new ImageNotDeletedException(IMAGE_NOT_DELETED);
                    }
                }
                throw new ImageNotFoundException(IMAGE_NOT_FOUND);
            }
        }

        // 검증 기능 추가 예정.
        return true;
    }
}
