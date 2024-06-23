package com.example.quiz_online_app_server.repository;

import com.example.quiz_online_app_server.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select distinct q.subject from Question q")
    List<String> findDistinctSubject();

    Page<Question> findBySubject(String subject, Pageable pageable);
}
