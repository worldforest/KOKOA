package com.web.kokoa.controller;

import com.web.kokoa.repository.CategoryRepo;
import com.web.kokoa.repository.SubtitlesRepo;
import com.web.kokoa.repository.VideoRepo;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiResponses(value = {
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure"),
})

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/subtitle")
public class SubtitlesController {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private VideoRepo videoRepo;
    @Autowired
    private SubtitlesRepo subtitlesRepo;

}
