package org.upgrad.repository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Follow;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FollowRepository extends CrudRepository<Follow,Integer>{
    @Query(nativeQuery = true,value="select * from follow where user_id=?1 and category_id=?2")
    Follow findbyUserIdAndCategoryId(int userId, int categforyId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="delete from follow where id=?1 ")
    void deleteFollowById(Integer id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="delete from follow where category_id=?1 and user_id=?2 ")
    void deleteByCategoryIdUserId(Integer cateforyId,Integer userId);
}
