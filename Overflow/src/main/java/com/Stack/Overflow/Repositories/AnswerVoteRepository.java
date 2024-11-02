package com.Stack.Overflow.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Stack.Overflow.Model.AnswerVote;

@Repository
public interface AnswerVoteRepository extends JpaRepository<AnswerVote, Long> {

}
