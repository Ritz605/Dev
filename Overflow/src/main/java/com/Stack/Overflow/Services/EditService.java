package com.Stack.Overflow.Services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Stack.Overflow.Enums.EditType;
import com.Stack.Overflow.Enums.ReviewStatus;
import com.Stack.Overflow.Model.AnswerEditRequest;
import com.Stack.Overflow.Model.AnswerRevisionHistory;
import com.Stack.Overflow.Model.Answers;
import com.Stack.Overflow.Model.Ban;
import com.Stack.Overflow.Model.EditRequest;
import com.Stack.Overflow.Model.QuestionEditRequest;
import com.Stack.Overflow.Model.QuestionRevisionHistory;
import com.Stack.Overflow.Model.Questions;
import com.Stack.Overflow.Model.User;
import com.Stack.Overflow.Repositories.AnswerEditRequestRepository;
import com.Stack.Overflow.Repositories.AnswerRepository;
import com.Stack.Overflow.Repositories.AnswerRevisionHistoryRepository;
import com.Stack.Overflow.Repositories.BanRepository;
import com.Stack.Overflow.Repositories.EditRequestRepository;
import com.Stack.Overflow.Repositories.QuestionEditRequestRepository;
import com.Stack.Overflow.Repositories.QuestionRevisionHistoryRepository;
import com.Stack.Overflow.Repositories.QuestionsRepository;
import com.Stack.Overflow.Repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EditService {

	private static final int MAX_REJECTIONS = 3;
	private static final int REQUIRED_APPROVALS = 2;
	private static final int REQUIRED_REJECTIONS = 2;

	@Autowired
	private EditRequestRepository editRequestRepository;
	@Autowired
	private QuestionsRepository questionsRepository;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BanRepository banRepository;
	@Autowired
	private QuestionRevisionHistoryRepository questionRevisionHistoryRepository;
	@Autowired
	private AnswerRevisionHistoryRepository answerRevisionHistoryRepository;
	@Autowired
	private QuestionEditRequestRepository questionEditRequestRepository;
	@Autowired
	private AnswerEditRequestRepository answerEditRequestRepository;
	
	public EditRequest reviewEdit(Long editRequestId, ReviewStatus status, Long reviewerId) {
		Optional<EditRequest> optionalEditRequest = editRequestRepository.findById(editRequestId);
		if(optionalEditRequest.isEmpty()) {
			throw new EntityNotFoundException("Edit request not found");
		}
		EditRequest editRequest = optionalEditRequest.get();
		User editor = editRequest.getEditor();
		if(status == ReviewStatus.APPROVED) {
			editRequest.setApprovalCount(editRequest.getApprovalCount() + 1);
			if(editRequest.getApprovalCount() >= REQUIRED_APPROVALS) {
				applyEdit(editRequest);
			}
		}else if(status == ReviewStatus.REJECTED) {
			editRequest.setRejectionCount(editRequest.getRejectionCount() + 1);
			if(editRequest.getRejectionCount() >= REQUIRED_REJECTIONS) {
				discardEdit(editRequest);
				handleUserRejection(editor);
			}
		}
		editRequestRepository.save(editRequest);
		return editRequest;
	}
	
	private void applyEdit(EditRequest editRequest) {
		if(editRequest instanceof QuestionEditRequest) {
			QuestionEditRequest questionEditRequest = (QuestionEditRequest) editRequest;
			Questions question = questionEditRequest.getQuestion();
			if(questionEditRequest.getEditSummary() != null) {
				editRequest.getEditor().setReputation(editRequest.getEditor().getReputation() + 2);
				question.setBody(questionEditRequest.getEditedBody());
				question.setCreatedDate(new Date());
				question.setTags(questionEditRequest.getEditedTags());
				question.setTitle(questionEditRequest.getEditedTitle());
				saveQuestionRevisionHistory(question, questionEditRequest.getEditor(), questionEditRequest.getEditSummary());
				questionsRepository.save(question);
			}
		}else if(editRequest instanceof AnswerEditRequest) {
			AnswerEditRequest answerEditRequest = (AnswerEditRequest) editRequest;
			Answers answer = answerEditRequest.getAnswer();
			if(answerEditRequest.getEditSummary() != null) {
				editRequest.getEditor().setReputation(editRequest.getEditor().getReputation() + 2);
				answer.setBody(answerEditRequest.getEditedBody());
				saveAnswerRevisionHistory(answer, answerEditRequest.getEditor(), answerEditRequest.getEditSummary());
				answerRepository.save(answer);
			}
		}
		editRequest.setReviewStatus(ReviewStatus.APPROVED);
	}
	
	private void discardEdit(EditRequest editRequest) {
		editRequest.setReviewStatus(ReviewStatus.REJECTED);
	}
	
	private void handleUserRejection(User editor) {
		editor.setRejectionCount(editor.getRejectionCount() + 1);
		userRepository.save(editor);
		if(editor.getRejectionCount() >= MAX_REJECTIONS) {
			banUser(editor);
		}
	}		
	private void banUser(User user) {
		Ban ban = new Ban();
		ban.setUser(user);
		ban.setBanStartDate(new Date());
		ban.setBanEndDate(Date.from(LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		banRepository.save(ban);
	}
	
	private void saveQuestionRevisionHistory(Questions question, User editor, String editSummary) {
	    QuestionRevisionHistory revisionHistory = new QuestionRevisionHistory();
	    revisionHistory.setQuestion(question);
	    revisionHistory.setTitle(question.getTitle());
	    revisionHistory.setBody(question.getBody());
	    revisionHistory.setEditSummary(editSummary);
	    revisionHistory.setUser(editor);
	    revisionHistory.setEditDate(new Date());

	    questionRevisionHistoryRepository.save(revisionHistory);
	}

	private void saveAnswerRevisionHistory(Answers answer, User editor, String editSummary) {
	    AnswerRevisionHistory revisionHistory = new AnswerRevisionHistory();
	    revisionHistory.setAnswer(answer);
	    revisionHistory.setBody(answer.getBody());
	    revisionHistory.setEditSummary(editSummary);
	    revisionHistory.setUser(editor);
	    revisionHistory.setEditDate(new Date());

	    answerRevisionHistoryRepository.save(revisionHistory);
	}
	
	
	public List<EditRequest> getPendingRequests() {
		List<QuestionEditRequest> questionEditRequests = questionEditRequestRepository.findAll();
		List<AnswerEditRequest> answerEditRequests = answerEditRequestRepository.findAll();
		
		List<EditRequest> pendingRequests = new ArrayList<EditRequest>();
		
		questionEditRequests.stream()
		.filter(request -> request.getReviewStatus() == ReviewStatus.PENDING)
		.forEach(pendingRequests::add);
		
		answerEditRequests.stream()
		.filter(request -> request.getReviewStatus() == ReviewStatus.PENDING)
		.forEach(pendingRequests::add);
		
		return pendingRequests;
	}
}
