package com.truenorth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truenorth.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
