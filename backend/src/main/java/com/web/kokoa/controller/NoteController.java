package com.web.kokoa.controller;


import com.web.kokoa.model.speechnote;
import com.web.kokoa.model.writenote;
import com.web.kokoa.repository.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@ApiResponses(value = {
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure"),
})

@CrossOrigin(origins = {"*"})
@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    SubtitlesRepo subtitlesRepo;

    @Autowired
    VideoRepo videoRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SpeechNoteRepo speechNoteRepo;

    @Autowired
    WriteNoteRepo writeNoteRepo;

    @PostMapping("/insert")
    @ApiOperation(value = "오답노트 추가", notes = "user num에 맞는 video, subtitle 오답노트 추가.")
    public ResponseEntity<String> addtoNote(@RequestParam String email, @RequestParam int videoid, @RequestParam int subtitleid, @RequestParam int type){
        int userid = userRepo.findUserByEmail(email).getId();
        String result = "";
        if(type==0){
            speechnote note = new speechnote();
            note.setUserid(userid);
            note.setSubtitleid(subtitleid);
            note.setVideoid(videoid);
            speechNoteRepo.save(note);
            result = "add to speechnote complete";
        }else{
            writenote note = new writenote();
            note.setUserid(userid);
            note.setSubtitleid(subtitleid);
            note.setVideoid(videoid);
            writeNoteRepo.save(note);
            result = "add to writenote complete";
        }
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @GetMapping("/load")
    @ApiOperation(value = "오답노트 조회", notes = "user num에 맞는 video, subtitle 오답노트 조회.")
    public ResponseEntity<HashMap<String, List<Object>>> loadUserNote(@RequestParam String email){

        int userid = userRepo.findUserByEmail("email").getId();
        List<writenote> writenotes = writeNoteRepo.findAllByUserid(userid);
        List<speechnote> speechnotes = speechNoteRepo.findAllByUserid(userid);

        HashMap<String,List<Object>> result = new HashMap<>();

        result.put("writenote", Collections.singletonList(writenotes));
        result.put("speechnote", Collections.singletonList(speechnotes));

        return new ResponseEntity<HashMap<String, List<Object>>>(result,HttpStatus.OK);
    }
}