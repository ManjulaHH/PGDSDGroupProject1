package org.upgrad.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.QuestionCategory;

import java.util.List;

@Repository
public interface QuestionCategoryRepository extends CrudRepository<QuestionCategory, Integer> {
    @Query(nativeQuery = true,value="select question_id from question_category where category_id=?1")
    List<Integer> findByCategoryId(Integer id);
}

