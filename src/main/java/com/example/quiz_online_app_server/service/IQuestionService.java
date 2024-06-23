package com.example.quiz_online_app_server.service;


import com.example.quiz_online_app_server.model.Question;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

public interface IQuestionService  {

    Question createQuestion(Question question);

    List<Question> getAllQuestion();

    Question updateQuestion(Long id, Question question) throws ChangeSetPersister.NotFoundException;

    Optional<Question> getQuestionById(Long id);

    List<String > getAllSubjects();

    void deleteQuestion(Long id);

    List<Question> getQuestionForUser(Integer numberOfQuestions, String subject);


}
