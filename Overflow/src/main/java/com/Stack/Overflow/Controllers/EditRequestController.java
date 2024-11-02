package com.Stack.Overflow.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Stack.Overflow.Enums.ReviewStatus;
import com.Stack.Overflow.Model.EditRequest;
import com.Stack.Overflow.Model.User;
import com.Stack.Overflow.Repositories.UserRepository;
import com.Stack.Overflow.Services.EditService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/editRequests")
public class EditRequestController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EditService editService;

	//review by revieers with privileges
	@PostMapping("/reviewEdit/{editRequestId}/{reviewerId}")
	public ResponseEntity<?> reviewEdit(@PathVariable Long editRequestId, @RequestParam ReviewStatus status,@RequestParam Long reviewerId) {
		User editor = userRepository.findById(reviewerId)
                .orElseThrow(() -> new EntityNotFoundException("Reviewer not found"));
        EditRequest editRequest = editService.reviewEdit(editRequestId, status, editor.getId());
        return ResponseEntity.ok(editRequest);
	}
	
	
	@GetMapping("/pending")
	public ResponseEntity<?> getPendingEditRequests(@RequestParam Long reviewerId) {
		Optional<User> reviewer = userRepository.findById(reviewerId);
		if(reviewer == null || reviewer.get().getReputation() < 2000) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient privileges");
		}
		List<EditRequest> pendingRequests = editService.getPendingRequests();
		return ResponseEntity.ok(pendingRequests);
	}
	
	
	
	
	
	
	
	
}
