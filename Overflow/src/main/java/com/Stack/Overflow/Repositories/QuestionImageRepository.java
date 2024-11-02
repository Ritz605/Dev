package com.Stack.Overflow.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Stack.Overflow.Model.QuestionImage;
import com.Stack.Overflow.Model.Questions;



@Repository
public interface QuestionImageRepository extends JpaRepository<QuestionImage, Long>{

	QuestionImage findByQuestion(Questions question);
}
