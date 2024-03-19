package com.maru.chaekmaru.review;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maru.chaekmaru.config.Config;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReviewService {

	@Autowired
	IReviewDao reviewDao;

	public ArrayList<ReviewDto> setReviews(int b_no) {

		return reviewDao.selectReviewsByBNo(b_no);
	}

	public int writeConfirm(ReviewDto reviewDto) {
		int result = -1;
		
		if(!isReviewWrite(reviewDto.getM_id(), reviewDto.getB_no())) {
			result = reviewDao.insertReview(reviewDto);
			
			if (result > 0) {
				return Config.REVIEW_WRITE_SUCCESS;
			} else {
				return Config.REVIEW_WRITE_FAIL;
			}			
		} else {
			return Config.REVIEW_ALREADY_EXIST;
		}
	}

	public int modifyConfirm(ReviewDto reviewDto) {
		int result = -1;

		result = reviewDao.modifyReview(reviewDto);

		if (result > 0) {
			return Config.REVIEW_MODIFY_SUCCESS;
		} else {
			return Config.REVIEW_MODIFY_FAIL;
		}
	}

	public int deleteConfirm(int r_no) {
		int result = -1;

		result = reviewDao.deleteReview(r_no);

		if (result > 0) {
			return Config.REVIEW_DELETE_SUCCESS;
		} else {
			return Config.REVIEW_DELETE_FAIL;
		}
	}

	public ArrayList<ReviewDto> setMyReview(String m_id) {
		return reviewDao.selectMyReviews(m_id);
	}

	public boolean isReviewWrite(String m_id, int b_no) {

		ReviewDto myReviewDto = reviewDao.isReviewWrite(m_id, b_no);
		
		
		if(myReviewDto != null) {
			return true;
		}
		
		return false;
	}

}
