package com.Stack.Overflow.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Stack.Overflow.Model.AnswerRevisionHistory;

@Repository
public interface AnswerRevisionHistoryRepository extends JpaRepository<AnswerRevisionHistory, Long> {

}
