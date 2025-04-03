package com.pliszok.quizzapp.dao;

import com.pliszok.quizzapp.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionDao extends JpaRepository<Question, Integer> {

}
