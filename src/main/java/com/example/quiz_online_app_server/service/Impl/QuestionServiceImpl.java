package com.example.quiz_online_app_server.service.Impl;

import com.example.quiz_online_app_server.model.Question;
import com.example.quiz_online_app_server.repository.QuestionRepository;
import com.example.quiz_online_app_server.service.IQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements IQuestionService {
    private final QuestionRepository questionRepository;

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public Question updateQuestion(Long id, Question question) throws ChangeSetPersister.NotFoundException {
        Optional<Question> theQuestion = this.getQuestionById(id);
        if(theQuestion.isPresent()){
            Question updateQuestion = theQuestion.get();
            updateQuestion.setQuestion(question.getQuestion());
            updateQuestion.setChoices(question.getChoices());
            updateQuestion.setCorrectAnswers(question.getCorrectAnswers());
            return questionRepository.save(updateQuestion);
        }
        else {
            throw new ChangeSetPersister.NotFoundException();
        }

    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<String> getAllSubjects() {
        return questionRepository.findDistinctSubject();
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> getQuestionForUser(Integer numberOfQuestions, String subject) {
        Pageable pageable = PageRequest.of(0, numberOfQuestions);

        return questionRepository.findBySubject(subject, pageable).getContent();
    }
}
