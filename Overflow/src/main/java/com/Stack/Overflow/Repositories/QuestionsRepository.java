package com.Stack.Overflow.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Stack.Overflow.Model.Questions;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long> {

	Page<Questions> findAllByUserId(Long userId, Pageable paging);

	Page<Questions> findAllByTitleContaining(String title, Pageable pageable);

	Page<Questions> findAllByOrderByCreatedDateDesc(Pageable paging);
	
	
}
