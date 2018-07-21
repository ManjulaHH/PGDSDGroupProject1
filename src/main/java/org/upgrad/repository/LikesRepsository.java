package org.upgrad.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Like;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LikesRepsository extends CrudRepository<Like,Integer>{
    @Query(nativeQuery = true,value="select * from likes where user_id=?1 and answer_id=?2")
    Like getByUserAndAnswer(int userId, int answerId);

    @Query(nativeQuery = true,value="select count(*) from likes where answer_id=?1")
    Integer getCountByAnswer(int answerId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="delete from likes where id=?1 ")
    void deleteLikesById(Long id);



    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="delete from likes where answer_id=?1 and user_id=?2")
    void deleteLikesByAnswerIdAndUserId(Integer answerId, Integer userId);
}

