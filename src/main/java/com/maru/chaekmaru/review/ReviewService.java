package com.maru.chaekmaru.review;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

	@Autowired
	IReviewDao reviewDao;

	public ArrayList<ReviewDto> setReviews(int b_no) {

		return reviewDao.selectReviewsByBNo(b_no);
	}

	public int writeConfirm(ReviewDto reviewDto) {
		int result = -1;

		result = reviewDao.insertReview(reviewDto);

		return result;
	}

	public int modifyConfirm(ReviewDto reviewDto) {
		int result = -1;

		result = reviewDao.modifyReview(reviewDto);

		return result;
	}

	public int deleteConfirm(int r_no) {
		int result = -1;

		result = reviewDao.deleteReview(r_no);

		return result;
	}

}
