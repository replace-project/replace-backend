package com.replace.re.place.image.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {


    /*
    이미지 저장은 AWS s3를 사용하기로 되어 있지만, 아직 S3 버킷을 생성하지 않아 임시로 이미지를 반환하는 코드를 구현.
     */
    public List<String> getImage(Long ImageId){
        ArrayList<String> images = new ArrayList<>();
        images.add("https://via.placeholder.com/300x200.png?text=Sample+Image");

        return images;
    }
}
