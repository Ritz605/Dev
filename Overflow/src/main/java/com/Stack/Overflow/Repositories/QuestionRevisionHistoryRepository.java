package com.Stack.Overflow.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Stack.Overflow.Model.QuestionRevisionHistory;

@Repository
public interface QuestionRevisionHistoryRepository extends JpaRepository<QuestionRevisionHistory, Long> {

}
