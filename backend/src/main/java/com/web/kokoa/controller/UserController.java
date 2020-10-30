package com.web.kokoa.controller;

import com.web.kokoa.model.user;
import com.web.kokoa.repository.UserRepo;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@ApiResponses(value = {
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure"),
})

@RestController
@CrossOrigin({"*"})
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepo userRepo;


    @GetMapping("/join")
    public ResponseEntity<String> join(@RequestParam String email) {
        String result = "";
        user user = userRepo.findUserByEmail(email);
        if (user == null) {
            user = new user();
            user.setEmail(email);
            userRepo.save(user);
            result = "회원가입이 완료되었습니다.";
        } else {
            result = "이미 존재하는 이메일 입니다.";
        }

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
