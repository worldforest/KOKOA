package com.web.kokoa.controller;

import com.web.kokoa.model.dictionary;
import com.web.kokoa.repository.CategoryRepo;
import com.web.kokoa.repository.SubtitlesRepo;
import com.web.kokoa.repository.VideoRepo;
import com.web.kokoa.service.TranslateService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.crizin.KoreanRomanizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApiResponses(value = {
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure"),
})

@CrossOrigin(origins = {"*"})
@Controller
@RequestMapping("/subtitle")
public class SubtitlesController {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private VideoRepo videoRepo;
    @Autowired
    private SubtitlesRepo subtitlesRepo;
    @Autowired
    private TranslateService translateService;


    @GetMapping("/dict")
    public ResponseEntity<List<dictionary>> komoran(@RequestParam int subtitleid) {
        List<dictionary> dict = new ArrayList<>();
        String content = subtitlesRepo.getContentById(subtitleid);
        dict = translateService.findDict(content);
        return new ResponseEntity<List<dictionary>>(dict, HttpStatus.OK);
    }


    @PostMapping("/speechtotext")
    public ResponseEntity<HashMap<String, String>> TransVoice(@RequestParam("files") MultipartFile file) throws Exception {

        String resultNaver = translateService.SpeechToTextNaver(file);
        String resultKaKao = translateService.SpeechToTextKaKao(file);
        HashMap<String, String> result = new HashMap<>();

        result.put("KaKaoResult", resultKaKao);
        result.put("NaverResult", resultNaver);

        return new ResponseEntity<HashMap<String, String>>(result, HttpStatus.OK);
    }

    @GetMapping("/roma")
    public ResponseEntity<String> romaService(@RequestParam String word) {
        KoreanRomanizer engToko = new KoreanRomanizer();
        String result = engToko.romanize(word);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }


}