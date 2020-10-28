package com.example.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = {
	      @ApiResponse(code = 401, message = "Unauthorized"),
	      @ApiResponse(code = 403, message = "Forbidden"),
	      @ApiResponse(code = 404, message = "Not Found"),
	      @ApiResponse(code = 500, message = "Failure"),
	})
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/sst")
public class SSTController {

}
