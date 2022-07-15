package com.admin.springbootadmin.repository;

import com.admin.springbootadmin.model.entity.Category;
import com.admin.springbootadmin.model.enumClass.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    //JPA QueryMethod
    Optional<Category> findByType(CategoryType type);
}
