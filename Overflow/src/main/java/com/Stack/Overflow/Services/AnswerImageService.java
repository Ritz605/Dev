package com.Stack.Overflow.Services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.Stack.Overflow.Model.Answers;
import com.Stack.Overflow.Model.QuestionImage;
import com.Stack.Overflow.Model.AnswerImage;
import com.Stack.Overflow.Model.Questions;
import com.Stack.Overflow.Repositories.AnswerRepository;
import com.Stack.Overflow.Repositories.AnswerImageRepository;
import com.Stack.Overflow.Repositories.QuestionsRepository;

@Service
public class AnswerImageService {

	@Autowired
	private AnswerImageRepository imageRepository;
	@Autowired
	private AnswerRepository answerRepository;
	
	
	public void storeFile(MultipartFile multipartFile, Long answerId) throws IOException {
		Optional<Answers> optionalAnswer = answerRepository.findById(answerId);
		if(optionalAnswer.isPresent()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			AnswerImage image = new AnswerImage();
			image.setName(fileName);
			image.setData(multipartFile.getBytes());
			image.setAnswers(optionalAnswer.get());
			image.setType(multipartFile.getContentType());
			imageRepository.save(image);
		}else {
			throw new IOException("Answer not found!");
		}
	}
	
	
}
