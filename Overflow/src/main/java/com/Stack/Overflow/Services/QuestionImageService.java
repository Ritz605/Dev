package com.Stack.Overflow.Services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.Stack.Overflow.Model.QuestionImage;
import com.Stack.Overflow.Model.Questions;
import com.Stack.Overflow.Repositories.QuestionImageRepository;
import com.Stack.Overflow.Repositories.QuestionsRepository;

@Service
public class QuestionImageService {

	@Autowired
	private QuestionImageRepository questionImageRepository;
	
	@Autowired
	private QuestionsRepository questionsRepository;
	
	public void storeFileForQuestion(MultipartFile multipartFile, Long questionId) throws IOException {
		Optional<Questions> optionalQuestion = questionsRepository.findById(questionId);
		if(optionalQuestion.isPresent()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			QuestionImage qImage = new QuestionImage();
			qImage.setData(multipartFile.getBytes());
			qImage.setName(fileName);
			qImage.setQuestion(optionalQuestion.get());
			qImage.setType(multipartFile.getContentType());
			questionImageRepository.save(qImage);
		}else {
			throw new IOException("Question not found!");
		}
	}
}
