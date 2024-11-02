package com.Stack.Overflow.Services;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Stack.Overflow.DTOs.AllQuestionsResponseDTO;
import com.Stack.Overflow.DTOs.AnswerDto;
import com.Stack.Overflow.DTOs.CommentDTO;
import com.Stack.Overflow.DTOs.QuestionDTO;
import com.Stack.Overflow.DTOs.QuestionSearchResponseDTO;
import com.Stack.Overflow.DTOs.SingleQuestionDTO;
import com.Stack.Overflow.Enums.ReviewStatus;
import com.Stack.Overflow.Enums.VoteType;
import com.Stack.Overflow.Model.AnswerVote;
import com.Stack.Overflow.Model.Answers;
import com.Stack.Overflow.Model.Ban;
import com.Stack.Overflow.Model.Comments;
import com.Stack.Overflow.Model.QuestionEditRequest;
import com.Stack.Overflow.Model.QuestionImage;
import com.Stack.Overflow.Model.QuestionRevisionHistory;
import com.Stack.Overflow.Model.QuestionVote;
import com.Stack.Overflow.Model.Questions;
import com.Stack.Overflow.Model.User;
import com.Stack.Overflow.Repositories.AnswerRepository;
import com.Stack.Overflow.Repositories.BanRepository;
import com.Stack.Overflow.Repositories.QuestionBanRepository;
import com.Stack.Overflow.Repositories.QuestionEditRequestRepository;
import com.Stack.Overflow.Repositories.QuestionImageRepository;
import com.Stack.Overflow.Repositories.QuestionRevisionHistoryRepository;
import com.Stack.Overflow.Repositories.AnswerImageRepository;
import com.Stack.Overflow.Repositories.QuestionsRepository;
import com.Stack.Overflow.Repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class QuestionsServiceImpl implements QuestionsService{
	
	public static final int SEARCH_RESULT_PER_PAGE = 5;

		
	
	@Autowired
	private QuestionsRepository questionsRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private AnswerImageRepository answerImageRepository;
	
	@Autowired
	private QuestionImageRepository questionImageRepository;
	
	@Autowired
	private QuestionEditRequestRepository questionEditRequestRepository;
	
	@Autowired
	private QuestionRevisionHistoryRepository questionRevisionHistoryRepository;
	
	@Autowired
	private QuestionBanRepository questionBanRepository;
	@Autowired
	private BanRepository banRepository;
	
	@Override
	public QuestionDTO addQuestion(QuestionDTO questionDTO) {
		Optional<User> userOptional = userRepository.findById(questionDTO.getUserId());
		if(userOptional.isPresent()) {
			Questions question = new Questions();
			question.setTitle(questionDTO.getTitle());
			question.setBody(questionDTO.getBody());
			question.setTags(questionDTO.getTags());
			question.setCreatedDate(new Date());
			
			Questions createdQuestions = questionsRepository.save(question);
			QuestionDTO createQuestionDTO = new QuestionDTO();
			createQuestionDTO.setId(createdQuestions.getId());
			createQuestionDTO.setTitle(createdQuestions.getTitle());
			return createQuestionDTO;
		}
		
		return null;
	}
	
	
	@Override
	public AllQuestionsResponseDTO getAllQuestions(int pageNumber) {
	Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE); 
	Page<Questions> questionPage = questionsRepository.findAll(paging);
	AllQuestionsResponseDTO allQuestionsResponseDTO = new AllQuestionsResponseDTO();
	allQuestionsResponseDTO.setQuestionDtoList(questionPage.getContent().stream().map(Questions::getQuestionDTO).collect(Collectors.toList()));
	allQuestionsResponseDTO.setPageNumber(questionPage.getPageable().getPageNumber());
	allQuestionsResponseDTO.setTotalPages(questionPage.getTotalPages());
	return allQuestionsResponseDTO;
	
	
	}

	@Override
	public SingleQuestionDTO getQuestionById(Long questionId, Long userId) {
		Optional<Questions> optionalQuestion = questionsRepository.findById(questionId);
		if(optionalQuestion.isPresent()) {
			SingleQuestionDTO singleQuestionDTO = new SingleQuestionDTO();
			List<AnswerDto> answerDtosList = new ArrayList<>();
			Questions existingQuestion = optionalQuestion.get();
			Optional<QuestionVote> optionalQuestionVote = existingQuestion.getQuestionVotedList().stream().filter(vote -> vote.getUser().getId().equals(userId)).findFirst();
			QuestionDTO questionDTO = optionalQuestion.get().getQuestionDTO();
			questionDTO.setVoted(0);
			if(optionalQuestionVote.isPresent()) {
				if(optionalQuestionVote.get().getVoteType().equals(VoteType.UPVOTE)) {
					questionDTO.setVoted(1);
				}else {
					questionDTO.setVoted(-1);
				}
			}
			questionDTO.setFile(questionImageRepository.findByQuestion(existingQuestion));
			singleQuestionDTO.setQuestionDTO(questionDTO);
			List<Answers> answerList = answerRepository.findAllByQuestionsId(questionId);
			for(Answers answer : answerList) {
				if(answer.isApproved()) {
					singleQuestionDTO.getQuestionDTO().setHasApprovedAnswer(true);
				}
				AnswerDto answerDto2 = answer.getAnswerDto();
				Optional<AnswerVote> optionalAnswerVote = answer.getAnswerVoteList().stream().filter(answerVote -> answerVote.getUser().getId().equals(userId)).findFirst();
				answerDto2.setVoted(0);
				if(optionalAnswerVote.isPresent()) {
					if(optionalAnswerVote.get().getVoteType().equals(VoteType.UPVOTE)) {
						answerDto2.setVoted(1);
					}else {
						answerDto2.setVoted(-1);
					}
				}
				answerDto2.setFile(answerImageRepository.findByAnswers(answer));
				answerDtosList.add(answerDto2);
				
				//Get Comment Dto List
		
				List<CommentDTO> commentDTOList = new ArrayList<CommentDTO>();
				answer.getCommentList().forEach(comment -> {
					CommentDTO commentDTO = comment.getCommentDto();
					commentDTOList.add(commentDTO);
				});
				answerDto2.setCommentDTOList(commentDTOList);
			}
			singleQuestionDTO.setAnswerDtosList(answerDtosList);
			return singleQuestionDTO;
		}
		return null;
	}


	@Override
	public AllQuestionsResponseDTO getQuestionsByUserId(Long userId, int pageNumber) {
		Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE); 
		Page<Questions> questionPage = questionsRepository.findAllByUserId(userId, paging);
		AllQuestionsResponseDTO allQuestionsResponseDTO = new AllQuestionsResponseDTO();
		allQuestionsResponseDTO.setQuestionDtoList(questionPage.getContent().stream().map(Questions::getQuestionDTO).collect(Collectors.toList()));
		allQuestionsResponseDTO.setPageNumber(questionPage.getPageable().getPageNumber());
		allQuestionsResponseDTO.setTotalPages(questionPage.getTotalPages());
		return allQuestionsResponseDTO;
	}
	
	
	
	@Override
	public QuestionSearchResponseDTO searchQuestionByTitle(String title, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum, SEARCH_RESULT_PER_PAGE);
		Page<Questions> questionPage;
		if(title == null || title.equals("null")) {
			questionPage = questionsRepository.findAll(pageable);
		}else {
			questionPage = questionsRepository.findAllByTitleContaining(title, pageable);
		}
		
		QuestionSearchResponseDTO questionSearchResponseDTO = new QuestionSearchResponseDTO();
		questionSearchResponseDTO.setPageNumber(questionPage.getPageable().getPageNumber());
		questionSearchResponseDTO.setQuestionDTOList(questionPage.stream().map(Questions::getQuestionDTO).collect(Collectors.toList()));
		questionSearchResponseDTO.setTotalPages(questionPage.getTotalPages());
		return questionSearchResponseDTO;
		
		
	}


	@Override
	public QuestionSearchResponseDTO getLatestQuestion(int pageNum) {
		Pageable paging = PageRequest.of(pageNum, SEARCH_RESULT_PER_PAGE);
		Page<Questions> questionPage;
		questionPage = questionsRepository.findAllByOrderByCreatedDateDesc(paging);
		QuestionSearchResponseDTO questionSearchResponseDTO = new QuestionSearchResponseDTO();
		questionSearchResponseDTO.setQuestionDTOList(questionPage.stream().map(Questions::getQuestionDTO).collect(Collectors.toList()));
		questionSearchResponseDTO.setPageNumber(questionPage.getPageable().getPageNumber());
		questionSearchResponseDTO.setTotalPages(questionPage.getTotalPages());
		return questionSearchResponseDTO;
	}


	//for editing question
	
	@Override
	public Questions directEdit(Long questionId, Long userId, QuestionDTO questionDTO) {
		Optional<Questions> optionalQuestion = questionsRepository.findById(questionId);
		Optional<User> optionalUser = userRepository.findById(userId);
		
		if(optionalQuestion.isPresent() && optionalUser.isPresent()) {
			User editor = optionalUser.get();
			Questions question = optionalQuestion.get();
			if(editor.equals(question.getUser()) || editor.getReputation() >= 2000) {
				saveRevision(question, editor, questionDTO.getEditSummary());
				
				question.setBody(questionDTO.getBody());
				question.setTags(questionDTO.getTags());
				question.setTitle(questionDTO.getTitle());
				editor.setReputation(editor.getReputation() + 2);
				return questionsRepository.save(question);
			}
		}
		return null;
	}


	@Override //for non-authors -- having repu less than 2000
	public QuestionEditRequest requestEdit(Long questionId, Long editorId, QuestionDTO questionDTO) {
		Optional<Questions> optionalQuestion = questionsRepository.findById(questionId);
		Optional<User> optionalUser = userRepository.findById(editorId);
		
		if(optionalQuestion.isPresent() && optionalUser.isPresent()) {
			if(isUserBanned(optionalUser.get())) {
				throw new RuntimeException("User is banned from editing.");
			}
			QuestionEditRequest editRequest = new QuestionEditRequest();
			editRequest.setCreatedDate(new Date());
			editRequest.setEditedBody(questionDTO.getBody());
			editRequest.setEditedTags(questionDTO.getTags());
			editRequest.setEditedTitle(questionDTO.getTitle());
			editRequest.setEditor(optionalUser.get());
			editRequest.setQuestion(optionalQuestion.get());
			editRequest.setEditSummary(questionDTO.getEditSummary());
			editRequest.setReviewStatus(ReviewStatus.PENDING);
			
			return questionEditRequestRepository.save(editRequest);
		}
		return null;
	}

	
	
	@Override
	public void saveRevision(Questions question, User editor, String editSummary) {
		QuestionRevisionHistory revisionHistory = new QuestionRevisionHistory();
		
		revisionHistory.setQuestion(question);
		revisionHistory.setTitle(question.getTitle());
		revisionHistory.setBody(question.getBody());
		revisionHistory.setTitle(question.getTitle());
		revisionHistory.setEditSummary(editSummary);
		revisionHistory.setUser(editor);
		
		questionRevisionHistoryRepository.save(revisionHistory);
		
	}


	
	private boolean isUserBanned(User user) {
		Ban ban = banRepository.findByUser(user);
		return ban != null && ban.getBanEndDate().after(new Date());
	}


	

	
	
	
	
	
	
	
}
