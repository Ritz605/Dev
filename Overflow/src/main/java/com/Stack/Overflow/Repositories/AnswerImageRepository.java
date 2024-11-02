package com.Stack.Overflow.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Stack.Overflow.Model.Answers;
import com.Stack.Overflow.Model.AnswerImage;
import com.Stack.Overflow.Model.Questions;

@Repository
public interface AnswerImageRepository extends JpaRepository<AnswerImage, Long> {

	AnswerImage findByAnswers(Answers answers);
	
	
}
