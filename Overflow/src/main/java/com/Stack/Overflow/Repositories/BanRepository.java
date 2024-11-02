package com.Stack.Overflow.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Stack.Overflow.Model.Ban;
import com.Stack.Overflow.Model.User;

import java.util.List;


@Repository
public interface BanRepository extends JpaRepository<Ban, Long>{
	Ban findByUser(User user);
}
