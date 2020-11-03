package com.web.kokoa.repository;

import com.web.kokoa.model.categorymember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepo extends JpaRepository<categorymember, String> {

    @Query(value = "select id from categorymember where name = :membername", nativeQuery = true)
    int findIdByName(String membername);

}
