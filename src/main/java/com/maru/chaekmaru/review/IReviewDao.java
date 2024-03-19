package com.maru.chaekmaru.review;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface IReviewDao {

	public ReviewDto selectReview(int r_no);

	public ArrayList<ReviewDto> selectReviewsByBNo(int b_no);

	public int insertReview(ReviewDto reviewDto);

	public int modifyReview(ReviewDto reviewDto);

	public int deleteReview(int r_no);

	public ArrayList<ReviewDto> selectMyReviews(String m_id);

	public ReviewDto isReviewWrite(@Param("m_id") String m_id, @Param("b_no") int b_no);

}
