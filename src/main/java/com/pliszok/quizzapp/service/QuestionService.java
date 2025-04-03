package com.pliszok.quizzapp.service;

import com.pliszok.quizzapp.Question;
import com.pliszok.quizzapp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//fghjfghjfgj
@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public List<Question> getAllQuestions(){
        return questionDao.findAll();
    }

}
