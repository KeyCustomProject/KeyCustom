package com.ho.edcustom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ho.edcustom.DTO.Request.GPTRequest;
import com.ho.edcustom.DTO.Response.HttpResponse;
import com.ho.edcustom.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OpenAiController {
    private final OpenAiService openAiService;
    @PostMapping("/ai/generate")
//    public ResponseEntity<HttpResponse> generate(@RequestBody GPTRequest DTO) {
//        HttpResponse Response = openAiService.aiResponse(DTO.getMessage());
//        return new ResponseEntity<>(Response,Response.getStatus());
//    }
    public ResponseEntity<HttpResponse> generate() throws ParseException {
        HttpResponse Response = openAiService.aiResponse();
        return new ResponseEntity<>(Response,Response.getStatus());
    }

}