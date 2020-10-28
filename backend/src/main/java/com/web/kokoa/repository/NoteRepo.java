package com.web.kokoa.repository;

import com.web.kokoa.model.note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepo extends JpaRepository<note,String> {
}
