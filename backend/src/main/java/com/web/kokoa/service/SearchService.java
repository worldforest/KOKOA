package com.web.kokoa.service;

import com.web.kokoa.model.subtitles;
import com.web.kokoa.model.video;
import com.web.kokoa.repository.SubtitlesRepo;
import com.web.kokoa.repository.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    VideoRepo videoRepo;
    @Autowired
    SubtitlesRepo subtitlesRepo;

    public Page<video> getVideo(int page, int Categoryid){
        Pageable pageable = PageRequest.of(page, 10);
        return videoRepo.findVideosByGroupid(pageable,Categoryid);
    }
    public List<subtitles>getSubtitles(int videoid){
        return subtitlesRepo.getAllByVideoid(videoid);
    }
}
