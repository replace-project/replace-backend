package com.replace.re.place.review.service;


import com.replace.re.place.review.dao.ReviewDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewDao reviewDao;
}
