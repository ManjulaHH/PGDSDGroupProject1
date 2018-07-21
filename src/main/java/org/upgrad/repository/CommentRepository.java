package org.upgrad.repository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Comment;

import javax.transaction.Transactional;
import java.util.List;

@Repository

public interface CommentRepository extends CrudRepository<Comment,Integer> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="update comment set content=?2,modifiedOn=now() where id=?1")
    void editComment(Integer id,String comment);

    @Query(nativeQuery = true,value="select * from comment where answer_id=?1")
    List<Comment> getAllCommentsByAnswerId(int answerId);

    @Query(nativeQuery = true,value="select * from comment where answer_id=?1 and user_id=?2")
    List<Comment> getAllCommentsByAnswerIdByLoggedInUser(int answerId, int userId);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="delete from comment where id=?1 ")
    void deletesById(int id);

    @Query(nativeQuery = true,value="select * from comment where id=?1")
    Comment getCommentById(int commentId);

}

