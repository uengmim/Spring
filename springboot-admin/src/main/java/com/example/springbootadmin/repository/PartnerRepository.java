package com.admin.springbootadmin.repository;

import com.admin.springbootadmin.model.entity.Category;
import com.admin.springbootadmin.model.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<Partner,Long> {


    //JPA QueryMethod
    List<Partner> findByCategory(Category category);
}
