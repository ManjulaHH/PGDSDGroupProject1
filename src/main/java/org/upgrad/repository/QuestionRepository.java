package org.upgrad.repository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Question;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {

    @Query(nativeQuery = true,value="select * from question where user_id=?1")
    List<Question> findByUserId(Integer id);

    @Query(nativeQuery = true,value="select * from question")
    List<Question> getAllQuestions();

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="delete from question where id=?1 ")
    void deletByQuestionId(int id);


    @Query(nativeQuery = true,value="select * from question where id=?1")
    Question findByQuestionId(Integer id);
}
