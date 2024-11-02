package com.Stack.Overflow.Services;

import com.Stack.Overflow.DTOs.AnswerDto;
import com.Stack.Overflow.DTOs.CommentDTO;
import com.Stack.Overflow.Model.AnswerEditRequest;
import com.Stack.Overflow.Model.Answers;
import com.Stack.Overflow.Model.User;

public interface AnswerService  {

	AnswerDto postAnswer(AnswerDto answerDto);
	
	AnswerDto approveAnswer(Long id);
	
	CommentDTO postCommentToAnswer(CommentDTO commentDTO);
	
	Answers directEdit(Long answerId, Long userId, String editedBody, String editSummary);
	
	AnswerEditRequest requestEdit(Long answerId, Long editorId, String editedBody, String editSummary);
	
	void saveRevision(Answers answer, User editor, String editSummary);

}
