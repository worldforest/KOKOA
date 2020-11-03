package com.web.kokoa.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Service
public class TranslateService {
    public enum HttpMethodType {POST, GET, DELETE}

    private static final String clientId = "jcej1kmd40";
    private static final String clientSecret = "lSNUrhaFoVJ8kQcT1X9yG5oQhZGmPVUayAnoGRly";


    private static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public String SpeechToTextNaver(MultipartFile file) {
        StringBuffer response = new StringBuffer();
        try {
            File voiceFile = convertMultiPartToFile(file);
            String language = "Kor";        // 언어 코드 ( Kor, Jpn, Eng, Chn )
            String apiURL = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=" + language;
            URL url = new URL(apiURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            OutputStream outputStream = conn.getOutputStream();
            FileInputStream inputStream = new FileInputStream(voiceFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            BufferedReader br = null;
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  // 오류 발생
                System.out.println("error!!!!!!! responseCode= " + responseCode);
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            String inputLine="";

            if (br != null) {
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            } else {
                System.out.println("error !!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        String answer =  response.toString().split(":")[1].split("}")[0].replace("\"","");
        return answer;
    }

    public String SpeechToTextKaKao(MultipartFile file) {

        String ret = new String();
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpPost postRequest = new HttpPost("https://kakaoi-newtone-openapi.kakao.com/v1/recognize"); //POST 메소드 URL 새성
            postRequest.setHeader("Content-Type", "application/octet-stream");
            postRequest.addHeader("Authorization", "KakaoAK a46d3510ed701ebe534e91be4f713d22");

            MultipartEntityBuilder params = MultipartEntityBuilder.create();
            params.addBinaryBody("data", convertMultiPartToFile(file));

            postRequest.setEntity(params.build());

            HttpResponse response = client.execute(postRequest);
            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = EntityUtils.toString(response.getEntity(), "UTF-8");

                StringTokenizer st = new StringTokenizer(body, "\n");
                ArrayList<String> al = new ArrayList<>();
                while (st.hasMoreTokens()) {
                    al.add(st.nextToken());
                }

                for (int i = 0; i < al.size(); i++) {
                    if (al.get(i).length() == 0) continue;
                    if (al.get(i).charAt(0) == '{') {
                        if (al.get(i).charAt(9) != 'f') continue;
                        System.out.println(al.get(i));
                        int idx = 31;
                        while (true) {
                            if (al.get(i).charAt(idx) == '"') break;
                            idx++;
                        }
                        ret = al.get(i).substring(31, idx);
                    }
                }
            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return ret;

    }


}
