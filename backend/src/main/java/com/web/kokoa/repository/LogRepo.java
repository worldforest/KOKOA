package com.web.kokoa.repository;

import com.web.kokoa.model.log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepo extends JpaRepository<log, String> {

    List<log> findByUserid(int id);
}
