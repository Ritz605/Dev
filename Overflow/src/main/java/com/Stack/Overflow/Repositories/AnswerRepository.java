package com.Stack.Overflow.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Stack.Overflow.Model.Answers;

@Repository
public interface AnswerRepository extends JpaRepository<Answers, Long> {

	List<Answers> findAllByQuestionsId(Long questionId);
}
