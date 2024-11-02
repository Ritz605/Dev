package com.Stack.Overflow.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Stack.Overflow.Model.EditRequest;

@Repository
public interface EditRequestRepository extends JpaRepository<EditRequest, Long> {

}
