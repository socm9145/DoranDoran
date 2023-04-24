package com.purple.hello.repo;

import com.purple.hello.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    long count();
    Question findByQuestionId(long questionId);
}
