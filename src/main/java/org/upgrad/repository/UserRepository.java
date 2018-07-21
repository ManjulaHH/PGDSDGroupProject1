package org.upgrad.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.User;
import org.upgrad.models.UserProfile;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(nativeQuery = true,value="select * from users where upper(username)= upper(?1)")
    List<User> findUserByUserName(String user);
    @Query(nativeQuery = true,value="select * from users ")
    Iterable<User> findAllUserDetails();
    @Query(nativeQuery = true,value="select * from users")
    List<User> findAllUsers();
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="delete from users where id=?1 ")
    void deleteUserById(int userId);

    @Query(nativeQuery = true,value="select * from users where upper(email)= upper(?1)")
    User findUserByEmail(String email);

    @Query(nativeQuery = true,value="select * from users where id=?1")
    User findUserByUserId(Integer userId);




}
