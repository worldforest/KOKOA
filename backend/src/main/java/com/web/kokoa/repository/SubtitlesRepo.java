package com.web.kokoa.repository;

import com.web.kokoa.model.subtitles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtitlesRepo extends JpaRepository<subtitles,String> {

    @Query(value = "select id, videoid, starttime, endtime, content, type from subtitles where videoid =:videoid order by id",nativeQuery = true)
    List<subtitles> getAllByVideoid(int videoid);
    @Query(value = "select content from subtitles where id =:subtitleid",nativeQuery = true)
    String getContentById(int subtitleid);
}
