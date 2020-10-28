package com.web.kokoa.controller;

import com.web.kokoa.repository.CategoryRepo;
import com.web.kokoa.repository.VideoRepo;
import com.web.kokoa.service.SearchService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@ApiResponses(value = {
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure"),
})

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoRepo videoRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private SearchService searchService;


}
