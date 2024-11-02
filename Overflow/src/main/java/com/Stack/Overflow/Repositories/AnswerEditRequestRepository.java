package com.Stack.Overflow.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Stack.Overflow.Model.AnswerEditRequest;

@Repository
public interface AnswerEditRequestRepository extends JpaRepository<AnswerEditRequest, Long> {

}
