package com.Stack.Overflow.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Stack.Overflow.Model.QuestionVote;

@Repository
public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {

}
