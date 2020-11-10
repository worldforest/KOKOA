package com.web.kokoa.repository;

import com.web.kokoa.model.writenote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WriteNoteRepo extends JpaRepository<writenote,String> {

    List<writenote> findAllByUserid(int userid);
}
