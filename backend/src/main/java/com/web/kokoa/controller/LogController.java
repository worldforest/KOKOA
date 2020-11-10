package com.web.kokoa.controller;


import com.web.kokoa.model.category;
import com.web.kokoa.model.log;
import com.web.kokoa.model.video;
import com.web.kokoa.repository.CategoryRepo;
import com.web.kokoa.repository.LogRepo;
import com.web.kokoa.repository.UserRepo;
import com.web.kokoa.repository.VideoRepo;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/log")
public class LogController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    LogRepo logRepo;
    @Autowired
    VideoRepo videoRepo;
    @Autowired
    CategoryRepo categoryRepo;

    @GetMapping("/load")
    public ResponseEntity<HashMap<String, List<Object>>> loadToLog(@RequestParam String email){
        HashMap<String, List<Object>> result = new HashMap<>();
        List<log> logList = logRepo.findByUserid(userRepo.findUserByEmail(email).getId());
        List<video> videoList = new ArrayList<>();
        List<String> categoryList = new ArrayList<>();

        for (log l: logList) {
            video temp = videoRepo.findById(l.getVideoid());
            String groupName = categoryRepo.findGroupnameById(l.getGroupid());
            if(!videoList.contains(temp))
                videoList.add(temp);
            if(!categoryList.contains(groupName))
                categoryList.add(groupName);
        }
        result.put("log", Collections.singletonList(logList));
        result.put("video", Collections.singletonList(videoList));
        result.put("group", Collections.singletonList(categoryList));

        return new ResponseEntity<HashMap<String, List<Object>>>(result, HttpStatus.OK);
    }

    @GetMapping("/insert")
    public ResponseEntity<String> saveToLog(@RequestParam int videoid, @RequestParam String email, @RequestParam int groupid){
        log logs = new log();
        logs.setUserid(userRepo.findUserByEmail(email).getId());
        logs.setVideoid(videoid);
        logs.setGroupid(groupid);
        String result = "";
        try{
            logRepo.save(logs);
            result = "log save complete";
        }catch (Exception e){
            e.printStackTrace();
            result = "log save fail";
        }
        return new ResponseEntity<String>(result,HttpStatus.OK);
    }
}
