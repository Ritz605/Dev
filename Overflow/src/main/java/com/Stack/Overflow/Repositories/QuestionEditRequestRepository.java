package com.Stack.Overflow.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Stack.Overflow.Model.QuestionEditRequest;

@Repository
public interface QuestionEditRequestRepository extends JpaRepository<QuestionEditRequest, Long> {

}
