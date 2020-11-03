package com.web.kokoa.repository;

import com.web.kokoa.model.video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepo extends JpaRepository<video, String> {

        Page<video> findVideosByGroupid(Pageable pageable, int id) ;


        @Query(value="select id, groupid, title, url from video where id in (select videoid from video_member where memberid =:id) ",nativeQuery = true)
        Page<video> findVideosByMemberid(Pageable pageable, int id);
}
