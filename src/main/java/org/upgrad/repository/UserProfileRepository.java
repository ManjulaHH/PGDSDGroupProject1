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
public interface UserProfileRepository extends CrudRepository<UserProfile, Integer> {

    //@Query(nativeQuery = true,value="select id,firstName,lastName,aboutMe,dob,contactNumber,country from user_profile where user_id=?1")
    @Query(nativeQuery = true,value="select * from user_profile where user_id=?1")

    List<UserProfile> findUserProfileByUserId(int id);
}
