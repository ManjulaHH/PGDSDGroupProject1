package org.upgrad.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Answer;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AnswerRepository extends CrudRepository<Answer,Integer>{
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="delete from answer where id=?1 ")
    void deleteAnswerById(int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="update answer set ans=?1 where id=?2 ")
    void updateById(String content,int id);

    @Query(nativeQuery = true,value="select * from  answer where question_id=?1")
    List<Answer> findByQuestionId(int id);

    @Query(nativeQuery = true,value="select * from  answer where user_id=?1")
    List<Answer> findByUserId(int id);

    @Query(nativeQuery = true,value="select * from  answer where id=?1")
    Answer findByAnswerId(int id);

}

