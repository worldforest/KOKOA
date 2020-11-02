package com.example.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.crizin.KoreanCharacter;
import net.crizin.KoreanRomanizer;
//swagger-ui ===>     <  http://localhost:8080/swagger-ui.html  > CTRL+CLICK!!!

@ApiResponses(value = {
	      @ApiResponse(code = 401, message = "Unauthorized"),
	      @ApiResponse(code = 403, message = "Forbidden"),
	      @ApiResponse(code = 404, message = "Not Found"),
	      @ApiResponse(code = 500, message = "Failure"),
	})
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/roma")
public class RomaController {

	
	@GetMapping
	public String m1(@RequestParam String s1) {
		KoreanRomanizer k = new KoreanRomanizer();
		return k.romanize(s1);
	}
	
	
}
