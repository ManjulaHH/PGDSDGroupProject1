package org.upgrad.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Notification;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification,Integer> {
    @Query(nativeQuery = true,value="select * from notification where user_id=?1 and read=false")
    List<Notification> getNewNotificationByUser(int id);

    @Query(nativeQuery = true,value="select * from notification where user_id=?1")
    List<Notification> getAllNotificationByUser(int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="update notification set read=true where id=?1")
    void markReadNewNotificationByUser(int id);


}

