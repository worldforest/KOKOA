package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.Token;
import kr.co.shineware.util.common.model.Pair;

@ApiResponses(value = {
	      @ApiResponse(code = 401, message = "Unauthorized"),
	      @ApiResponse(code = 403, message = "Forbidden"),
	      @ApiResponse(code = 404, message = "Not Found"),
	      @ApiResponse(code = 500, message = "Failure"),
	})
@CrossOrigin(origins = { "*" })
@RestController()
@RequestMapping("/dict")

public class DictionaryController {
	
	static String myPos[]= {"NN","MA","VV","VA"};
	
	@GetMapping
	public Object m1(@RequestParam String s1) {
		
		
		
		
		
		return 1;
	}
	
	public static void main(String[] args) {
		String s1="박사님 이게 제대로 된 것 맞나요?";
		
		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		
		ArrayList<String> list = new ArrayList<>();
		
		List<Token> tokens = komoran.analyze(s1).getTokenList();
		for(Token token : tokens) {
			if(isMatch(token.getPos())) {
				if(token.getPos().charAt(0)=='V') list.add(token.getMorph()+"다");
				else list.add(token.getMorph());
			}
		}
		
		for(String t : list) {
			System.out.println(t);
		}
		
		
//		try {
//			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
//			String query="https://glosbe.com/gapi/translate?from=kor&dest=eng&format=json&pretty=true&phrase=딸기";
//			HttpPost postRequest = new HttpPost(query);
//			
//			
//			HttpResponse response = client.execute(postRequest);
//			if (response.getStatusLine().getStatusCode() == 200) {
//				ResponseHandler<String> handler = new BasicResponseHandler();
//				String body = EntityUtils.toString(response.getEntity(), "UTF-8");		
//				System.out.println(body);
//			} else {
//				System.out.println("response is error : " + response.getStatusLine().getStatusCode());
//			}
//		} catch (Exception e){
//			System.err.println(e.toString());
//		}
		
	}
	
	public static boolean isMatch(String pos) {
		String tmp = pos.substring(0, 2);
		for(String p : myPos) {
			if(p.equals(tmp)) return true;
		}
		
		return false;
	}
	
}
