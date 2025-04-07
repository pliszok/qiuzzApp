package com.pliszok.quizzapp.dao;

import com.pliszok.quizzapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategoryIgnoreCase(String category);

    void deleteById(Integer id);

    @Query(value = "SELECT * FROM Question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numOfQuestions", nativeQuery = true)
    List<Question> findRandomQuestionsByCategoryIgnoreCase(String category, int numOfQuestions);
}
