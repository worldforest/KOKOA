package com.web.kokoa.repository;


import com.web.kokoa.model.writenote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WriteNoteRepo extends JpaRepository<writenote,String> {

    List<writenote> findAllByUserid(int userid);

    @Query(value = "select id, subtitleid, userid, videoid, engsubtitleid from writenote where subtitleid =:subtitleid and userid =:userid",nativeQuery = true)
    writenote getBySubtitleidandUserid(int subtitleid, int userid);
}
