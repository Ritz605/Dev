package com.Stack.Overflow.Services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Stack.Overflow.DTOs.AnswerDto;
import com.Stack.Overflow.DTOs.CommentDTO;
import com.Stack.Overflow.DTOs.QuestionDTO;
import com.Stack.Overflow.Enums.ReviewStatus;
import com.Stack.Overflow.Model.AnswerEditRequest;
import com.Stack.Overflow.Model.AnswerRevisionHistory;
import com.Stack.Overflow.Model.Answers;
import com.Stack.Overflow.Model.Ban;
import com.Stack.Overflow.Model.Comments;
import com.Stack.Overflow.Model.QuestionEditRequest;
import com.Stack.Overflow.Model.QuestionRevisionHistory;
import com.Stack.Overflow.Model.Questions;
import com.Stack.Overflow.Model.User;
import com.Stack.Overflow.Repositories.AnswerEditRequestRepository;
import com.Stack.Overflow.Repositories.AnswerRepository;
import com.Stack.Overflow.Repositories.AnswerRevisionHistoryRepository;
import com.Stack.Overflow.Repositories.BanRepository;
import com.Stack.Overflow.Repositories.CommentRepository;
import com.Stack.Overflow.Repositories.QuestionsRepository;
import com.Stack.Overflow.Repositories.UserRepository;

@Service
public class AnswerSerivceImpl implements AnswerService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QuestionsRepository questionsRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private AnswerRevisionHistoryRepository answerRevisionHistoryRepository;
	@Autowired
	private AnswerEditRequestRepository answerEditRequestRepository;
	@Autowired
	private BanRepository banRepository;

	@Override
	public AnswerDto postAnswer(AnswerDto answerDto) {
		Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
		Optional<Questions> optionalQuestion = questionsRepository.findById(answerDto.getQuestionId());
		if(optionalUser.isPresent() && optionalQuestion.isPresent()) {
			Answers answers = new Answers();
			answers.setBody(answerDto.getBody());
			answers.setCreatedDate(new Date());
			answers.setUser(optionalUser.get());
			answers.setQuestions(optionalQuestion.get());
			Answers createdAnswer = answerRepository.save(answers);
			AnswerDto createdAnswerDto = new AnswerDto();
			createdAnswerDto.setId(createdAnswer.getId());
			return createdAnswerDto;
		}
		return null;
	}
	
	@Override
	public AnswerDto approveAnswer(Long answerId, Long approverId) {
		Optional<Answers> optionalAnswer = answerRepository.findById(answerId);
		Optional<User> optionalApprover = userRepository.findById(approverId);
		if(optionalAnswer.isPresent() && optionalApprover.isPresent()) {
			Answers answer = optionalAnswer.get();
			answer.setApproved(true);
			answer.getUser().setReputation(answer.getUser().getReputation() + 15);
			optionalApprover.get().setReputation(optionalApprover.get().getReputation() + 2);
		    Answers	approvedAnswer = answerRepository.save(answer);
		    AnswerDto approvedAnswerDto = new AnswerDto();
		    approvedAnswerDto.setId(approvedAnswer.getId());
		    return approvedAnswerDto;
		}
		
		return null;
	}

	@Override
	public CommentDTO postCommentToAnswer(CommentDTO commentDTO) {
		Optional<Answers> optionalAnswer = answerRepository.findById(commentDTO.getAnswer_id());
		Optional<User> optionalUser = userRepository.findById(commentDTO.getUser_id());
		if(optionalAnswer.isPresent() && optionalUser.isPresent()) {
			Comments comment = new Comments();
			comment.setAnswers(optionalAnswer.get());
			comment.setBody(commentDTO.getBody());
			comment.setCreatedDate(new Date());
			comment.setUser(optionalUser.get());
			Comments createdComment = commentRepository.save(comment);
			CommentDTO createdCommentDTO = new CommentDTO();
			createdCommentDTO.setId(createdComment.getId());
			return createdCommentDTO;
		}
		
		return null;
	}
	
	
	
	@Override
	public Answers directEdit(Long answerId, Long userId, String editedBody, String editSummary) {
		Optional<Answers> optionalAnswer = answerRepository.findById(answerId);
		Optional<User> optionalUser = userRepository.findById(userId);
		
		if(optionalAnswer.isPresent() && optionalUser.isPresent()) {
			User editor = optionalUser.get();
			Answers answer = optionalAnswer.get();
			if(editor.equals(answer.getUser()) || editor.getReputation() >= 2000) {
				saveRevision(answer, editor, editSummary);
				
				answer.setBody(editedBody);
				
				return answerRepository.save(answer);
			}
		}
		return null;
	}


	@Override //for non-authors -- having repu less than 2000
	public AnswerEditRequest requestEdit(Long answerId, Long editorId, String editedBody, String editSummary) {
        Optional<Answers> optionalAnswer = answerRepository.findById(answerId);
        Optional<User> optionalUser = userRepository.findById(editorId);

        if (optionalAnswer.isPresent() && optionalUser.isPresent()) {
            if (isUserBanned(optionalUser.get())) {
                throw new RuntimeException("User is banned from editing.");
            }
            AnswerEditRequest editRequest = new AnswerEditRequest();
            editRequest.setAnswer(optionalAnswer.get());
            editRequest.setEditor(optionalUser.get());
            editRequest.setEditedBody(editedBody);
            editRequest.setEditSummary(editSummary);
            editRequest.setCreatedDate(new Date());
            editRequest.setReviewStatus(ReviewStatus.PENDING);
            return answerEditRequestRepository.save(editRequest);
        }
        return null;
    }


	
	
	@Override
	public void saveRevision(Answers answer, User editor, String editSummary) {
		AnswerRevisionHistory revisionHistory = new AnswerRevisionHistory();
		
		revisionHistory.setAnswer(answer);
		revisionHistory.setBody(answer.getBody());
		revisionHistory.setEditSummary(editSummary);
		revisionHistory.setUser(editor);
		
		answerRevisionHistoryRepository.save(revisionHistory);
		
	}


	
	private boolean isUserBanned(User user) {
		Ban ban = banRepository.findByUser(user);
		return ban != null && ban.getBanEndDate().after(new Date());
	}


	
}
