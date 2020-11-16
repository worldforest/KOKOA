package com.web.kokoa.repository;

import com.web.kokoa.model.category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<category,String>{

    @Query(value = "select id from category where groupname = :groupname", nativeQuery = true)
    int findIdByGroupname(String groupname);

    @Query(value = "select groupname from category where id = :id", nativeQuery = true)
    String findGroupnameById(int id);
}
