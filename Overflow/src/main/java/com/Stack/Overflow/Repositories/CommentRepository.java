package com.Stack.Overflow.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Stack.Overflow.Model.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long>{

}
