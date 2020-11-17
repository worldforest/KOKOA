package com.web.kokoa.service;

import com.web.kokoa.model.speechnote;
import com.web.kokoa.model.subtitles;
import com.web.kokoa.model.video;
import com.web.kokoa.model.writenote;
import com.web.kokoa.repository.SpeechNoteRepo;
import com.web.kokoa.repository.SubtitlesRepo;
import com.web.kokoa.repository.VideoRepo;
import com.web.kokoa.repository.WriteNoteRepo;
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
    @Autowired
    SpeechNoteRepo speechNoteRepo;
    @Autowired
    WriteNoteRepo writeNoteRepo;

    public Page<video> getVideo(int page, int Categoryid) {
        Pageable pageable = PageRequest.of(page, 10);
        return videoRepo.findVideosByGroupid(pageable, Categoryid);
    }

    public List<subtitles> getSubtitles(int videoid) {
        return subtitlesRepo.getAllByVideoid(videoid);
    }

    public Page<video> getVideoMember(int page, int memberid) {
        Pageable pageable = PageRequest.of(page, 10);
        return videoRepo.findVideosByMemberid(pageable, memberid);
    }

    public Page<speechnote> getSpeechNote(int page, int memberid){
        Pageable pageable = PageRequest.of(page,10);
        return speechNoteRepo.findAllByUserid(pageable, memberid);
    }

    public Page<writenote> getWriteNote(int page, int memberid){
        Pageable pageable = PageRequest.of(page,10);
        return writeNoteRepo.findAllByUserid(pageable, memberid);
    }
}
