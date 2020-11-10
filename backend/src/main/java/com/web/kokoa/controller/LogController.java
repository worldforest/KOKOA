package com.web.kokoa.controller;


import com.web.kokoa.model.log;
import com.web.kokoa.repository.LogRepo;
import com.web.kokoa.repository.UserRepo;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/load")
    public ResponseEntity<HashMap<String, List<log>>> loadToLog(@RequestParam String email){
        HashMap<String, List<log>> result = new HashMap<>();
        List<log> list = logRepo.findByUserid(userRepo.findUserByEmail(email).getId());
        result.put("userlog",list);

        return new ResponseEntity<HashMap<String, List<log>>>(result, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> saveToLog(@RequestParam int videoid, @RequestParam int userid){
        log logs = new log();
        logs.setUserid(userid);
        logs.setVideoid(videoid);
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
