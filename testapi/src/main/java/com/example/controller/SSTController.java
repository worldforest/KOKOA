package com.example.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sun.xml.messaging.saaj.packaging.mime.internet.ContentType;

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
	
	
	@PostMapping
	public Object m1(@RequestParam("file") MultipartFile file) {
		
		String ret=new String();
		try {
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpPost postRequest = new HttpPost("https://kakaoi-newtone-openapi.kakao.com/v1/recognize"); //POST 메소드 URL 새성 
			postRequest.setHeader("Content-Type", "application/octet-stream");
			postRequest.addHeader("Authorization", "KakaoAK a46d3510ed701ebe534e91be4f713d22");
//			postRequest.addHeader("Transfer-Encoding", "chunked");
//			postRequest.addHeader("X-DSS-Service", "DICTATION");

//			 wav(또는 raw), mono channel, 16000 Hz samplerate, 16 bit depth
//			postRequest.setEntity(new StringEntity(jsonMessage)); //json 메시지 입력
			MultipartEntityBuilder params = MultipartEntityBuilder.create();
			params.addBinaryBody("data", convert(file));
			 
			postRequest.setEntity(params.build());

			HttpResponse response = client.execute(postRequest);
			//Response 출력
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = EntityUtils.toString(response.getEntity(), "UTF-8");
				JSONObject jObject = new JSONObject(body);
				

				
				StringTokenizer st=new StringTokenizer(body,"\n");
				ArrayList<String> al=new ArrayList<>();
				while(st.hasMoreTokens()) {
					al.add(st.nextToken());
				}
				
				for(int i=0;i<al.size();i++) {
					if(al.get(i).length()==0) continue;
					if(al.get(i).charAt(0)=='{') {
						if(al.get(i).charAt(9)!='f') continue;
						System.out.println(al.get(i));
						int idx=31;
						while(true) {
							if(al.get(i).charAt(idx)=='"') break;
							idx++;
						}
						ret=al.get(i).substring(31,idx);
					}
				}
				
				System.out.println(body);
			} else {
				System.out.println("response is error : " + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e){
			System.err.println(e.toString());
		}
		
		return ret;

	}
	
	public File convert(MultipartFile file) throws IOException
	{    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}

}
