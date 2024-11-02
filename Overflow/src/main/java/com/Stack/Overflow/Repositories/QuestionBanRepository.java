package com.Stack.Overflow.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Stack.Overflow.Model.Ban;
import com.Stack.Overflow.Model.User;

@Repository
public interface QuestionBanRepository extends JpaRepository<Ban, Long> {
	Ban findByUser(User user);
}
