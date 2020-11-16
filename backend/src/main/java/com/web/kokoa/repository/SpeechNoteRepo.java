package com.web.kokoa.repository;

import com.web.kokoa.model.speechnote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeechNoteRepo extends JpaRepository<speechnote,String> {

    List<speechnote> findAllByUserid(int userid);

    @Query(value = "select id, subtitleid, userid, videoid, engsubtitleid from speechnote where subtitleid =:subtitleid and userid =:userid",nativeQuery = true)
    speechnote getBySubtitleidandUserid(int subtitleid, int userid);
}
