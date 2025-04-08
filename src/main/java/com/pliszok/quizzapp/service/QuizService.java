package com.pliszok.quizzapp.service;

import com.pliszok.quizzapp.dao.QuestionDao;
import com.pliszok.quizzapp.dao.QuizDao;
import com.pliszok.quizzapp.model.Question;
import com.pliszok.quizzapp.model.QuestionWrapper;
import com.pliszok.quizzapp.model.Quiz;
import com.pliszok.quizzapp.model.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numOfQuestions, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByCategoryIgnoreCase(category, numOfQuestions);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Quiz created.", HttpStatus.CREATED);

    }

    @Transactional
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB =  quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Question q : questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(),
                    q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<Integer> calculateResult (Integer id, List<Response> responses){

        /*Quiz quiz = quizDao.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));*/

        Quiz quiz = quizDao.findById(id).get();

        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i = 0;
        for (Response response : responses){
            if(response.getAnswer().equals(questions.get(i).getRightAnswer()))
            right++;
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
