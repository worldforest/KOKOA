package com.web.kokoa.repository;

import com.web.kokoa.model.log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepo extends JpaRepository<log, String> {

    @Query(value = "select distinct(videoid),groupid, userid, id from log where userid =:id order by id desc limit 5",nativeQuery = true)
    List<log> findByUserid(int id);
}
