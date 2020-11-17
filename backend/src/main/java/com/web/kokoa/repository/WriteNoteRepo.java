package com.web.kokoa.repository;


import com.web.kokoa.model.writenote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WriteNoteRepo extends JpaRepository<writenote,String> {

    @Query(value = "select id, subtitleid, userid, videoid, engsubtitleid from writenote where userid =:userid",countQuery="select count(id) from writenote where userid =:userid",nativeQuery = true)
    Page<writenote> findAllByUserid(Pageable pageable, int userid);

    @Query(value = "select id, subtitleid, userid, videoid, engsubtitleid from writenote where subtitleid =:subtitleid and userid =:userid",nativeQuery = true)
    writenote getBySubtitleidandUserid(int subtitleid, int userid);

    writenote findById(int noteid);
}
