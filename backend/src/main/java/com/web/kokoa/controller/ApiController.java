package com.web.kokoa.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;

//import io.swagger.annotations.ApiOperation;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

//@CrossOrigin(origins = { "*" }, maxAge = 6000)
//@RestController
//@RequestMapping("/api/api")
public class ApiController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	static String apikey = "C28E50AD08896D5E34F647ECF279888D";

//	@ApiOperation(value = "한국어 검색", notes = "검색 결과 보여줘야지")
//	@GetMapping("search")
	public ResponseEntity<String> search(@RequestParam String str) {
		BufferedReader br = null;
		try {
			String urlstr = "https://krdict.korean.go.kr/api/search" + "?key=" + apikey
					+ "&part=word&sort=popular&num=10&q=?" + str + "&translated=y&trans_lang=1";
			URL url = new URL(urlstr);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
			String result = "";
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("<trans_word>") || line.contains("<trans_dfn>")) {
					String[] temp = line.split("\\[");
					String[] temp2 = temp[2].split("]");
					result = result + temp2[0] + "\n";
				}
			}
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

//	@SuppressWarnings("null")
//	@ApiOperation(value = "형태소 분석", notes = "문장보내면 형태소 검색")
//	@GetMapping("komoran")
	public ResponseEntity<String> komoran(@RequestParam String strToAnalyze) {
		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);
		System.out.println(analyzeResultList.getPlainText());
		List<Token> tokenList = analyzeResultList.getTokenList();
//		List<String> words = null; 
		String words = null;
		for (Token token : tokenList) {
			if (token.getPos().equals("NNG")) {
				System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(),
						token.getPos());
				words = token.getMorph();
			}
		}

		BufferedReader br = null;
		String str = words;
		try {
			String urlstr = "https://krdict.korean.go.kr/api/search" + "?key=" + apikey
					+ "&part=word&sort=popular&num=10&q=?" + str + "&translated=y&trans_lang=1";
			URL url = new URL(urlstr);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
			String result = "";
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("<trans_word>") || line.contains("<trans_dfn>")) {
					String[] temp = line.split("\\[");
					String[] temp2 = temp[2].split("]");
					result = result + temp2[0] + "\n";
				}
			}
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
}