package com.web.kokoa.controller;

import com.web.kokoa.model.video;
import com.web.kokoa.repository.CategoryRepo;
import com.web.kokoa.service.SearchService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@ApiResponses(value = {
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure"),
})

@CrossOrigin(origins = {"*"})
@Controller
@RequestMapping("/main")
public class MainController {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private SearchService searchService;

    @GetMapping("/idol/{page}")
    @ApiOperation(value = "좋아하는 가수 검색", notes = "category num과 일치하는 video 반환")
    private Object SearchIdolVideo(@RequestParam String groupname, @PathVariable int page) {
        int GroupId = categoryRepo.findIdByGroupname(groupname);
        Page<video> list = searchService.getVideo(page, GroupId);
        return new ResponseEntity<Page<video>>(list, HttpStatus.OK);
    }
}
