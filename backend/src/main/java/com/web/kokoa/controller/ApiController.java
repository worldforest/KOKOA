package com.web.kokoa.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;

//import io.swagger.annotations.ApiOperation;
//import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
//import kr.co.shineware.nlp.komoran.core.Komoran;
//import kr.co.shineware.nlp.komoran.model.KomoranResult;
//import kr.co.shineware.nlp.komoran.model.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.kokoa.resultDto.Dictionary;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/dict")
public class ApiController {

	static String myPos[] = { "NN", "MA", "VV", "VA" };
	static String apikey = "C28E50AD08896D5E34F647ECF279888D";

	public static boolean isMatch(String pos) {
		String tmp = pos.substring(0, 2);
		for (String p : myPos) {
			if (p.equals(tmp))
				return true;
		}

		return false;
	}

	@GetMapping("komoran")
	public ResponseEntity<List<Dictionary>> komoran(@RequestParam String strToAnalyze) {
		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);
		System.out.println(analyzeResultList.getPlainText());
		List<Token> tokenList = analyzeResultList.getTokenList();
		ArrayList<String> words = new ArrayList<String>();

		for (Token token : tokenList) {

			if (isMatch(token.getPos())) {
				if (token.getPos().contains("V")) {
					words.add(token.getMorph() + "다");
				} else {
					words.add(token.getMorph());
				}
			}
		}

		BufferedReader br = null;
		List<Dictionary> list = new ArrayList<Dictionary>();
		for (String str : words) {
			try {
				String urlstr = "https://krdict.korean.go.kr/api/search" + "?key=" + apikey
						+ "&part=word&sort=popular&num=10&q=" + str + "&translated=y&trans_lang=1";
				URL url = new URL(urlstr);
				HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
				urlconnection.setRequestMethod("GET");
				br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
				String line;

				Dictionary dict = new Dictionary();
				dict.setKor(str);
				while ((line = br.readLine()) != null) {
					if (line.contains("<trans_word>")||line.contains("<trans_dfn>")) {
						String[] temp = line.split("\\[");
						String[] temp2 = temp[2].split("]");
						
						if(line.contains("<trans_word>")) {
							dict.setEng(temp2[0]);
						}
						else if(line.contains("<trans_dfn>")) {
							dict.setDfn(temp2[0]);
						}
						if(dict.getDfn()!=null) {
							break;
						}
					}
				}
				if (dict.getEng() == null || dict.getDfn() == null) {
					dict.setEng("영어단어를 찾지 못했습니다.");
					dict.setDfn("뜻을 찾지 못했습니다.");
				}
				list.add(dict);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return new ResponseEntity<List<Dictionary>>(list, HttpStatus.OK);
	}
}