package com.example.quiz_online_app_server.controller;

import com.example.quiz_online_app_server.model.Question;
import com.example.quiz_online_app_server.service.IQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")

public class QuestionController {
    private final IQuestionService questionService;

    //function create a new question
    @PostMapping("/create-new-question")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question){
        Question createQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(CREATED).body(createQuestion);
    }

    //function get all questions
    @GetMapping("/all-questions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        List<Question> questions = questionService.getAllQuestion();
        return ResponseEntity.ok(questions);
    }

    //function get a question by id
    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Question> theQuestion = questionService.getQuestionById(id);
        if(theQuestion.isPresent()){
            return ResponseEntity.ok(theQuestion.get());
        }else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    //function update a question by id
    @PutMapping("question/{id}/update")
    public  ResponseEntity<Question> updateQuestion(@PathVariable Long id,
                                                    @RequestBody Question question)
            throws ChangeSetPersister.NotFoundException {
        Question theQuestion = questionService.updateQuestion(id, question);
        return ResponseEntity.ok(theQuestion);
    }

    //function delete a function by id
    @DeleteMapping("question/{id}/delete")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id)
    {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    //function get all subjects
    @GetMapping("/subjects")
    public ResponseEntity<List<String>> getAllSubject(){
        List<String> subjects = questionService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    //function get all questions for user
    @GetMapping("/fetch-questions-for-user")
    public ResponseEntity<List<Question>> getQuestionForUser(
            @RequestParam Integer numberOfQuestion, @RequestParam String subject){
        List<Question> allQuestions = questionService.getQuestionForUser(numberOfQuestion, subject);
        List<Question> mutableQuestions = new ArrayList<>(allQuestions);
        Collections.shuffle(mutableQuestions);
        int availableQuestions = Math.min(numberOfQuestion, mutableQuestions.size());
        List<Question> randomQuestions = mutableQuestions.subList(0, availableQuestions);
        return ResponseEntity.ok(randomQuestions);
    }
}
