package org.upgrad.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Category;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Integer> {
    @Query(nativeQuery = true,value="select * from category where id=?1")
    Category findCategoryById(int id);

    @Query(nativeQuery = true,value="select * from  where user_id=?1 and answer_id=?2")
    Iterable<String> getByUserAndAnswer(int userId, int answerId);

    @Query(nativeQuery = true,value="select * from  where id in (?1)")
    List<Category> findByIds(Set<Integer> ids);

    @Query(nativeQuery = true,value="select * from  category")
    List<Category> findAllCategories();

  /*  @Transactional
    @Modifying
    @Query(nativeQuery = true,value="delete from likes where id=?1 ")
    void findByUserIdAndCategoryId(Long id);*/

}
