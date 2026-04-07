package com.ho.edcustom.service;

import com.ho.edcustom.DTO.Response.HttpResponse;
import com.ho.edcustom.enumSet.ErrorCode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OpenAiService {
    private final ChatModel chatModel;


    public OpenAiService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }
    public HttpResponse aiResponse() throws ParseException {

        ChatResponse chatResponse = chatModel.call(
                new Prompt("다음 조건에 맞는 키보드 추천 을 JSON 형식으로 제공해주세요:\n" +
                        "                \n" +
                        "                조건:\n" +
                        "                - barebone은 베어본으로 색을 RGB Hex 코드로 랜덤 추천\n" +
                        "                - switch는 키보드의 축으로 적축,갈축,청축,흑축,황축 5개중에 하나 랜덤으로 추천\n" +
                        "                - keycap의 키캡으로 색을 RGB Hex 코드로 랜덤 추천\n" +
                        "                - 반드시 JSON 형식만 단답으로 보낼 것\n" +
                        "                - 완성된 키보드 설명은 80자 이상으로 설명 할 것" +
                        "\n" +
                        "                응답 형식:\n" +
                        "                {\n" +
                        "                    \"keyboards\": [\n" +
                        "                        {\n" +
                        "                            \"barebone\": \"hex 코드\",\n" +
                        "                            \"switch\": \"축 색깔\",\n" +
                        "                            \"keycap\": \"hex 코드\"\n" +
                        "                        }\n" +
                        "                    ],\n" +
                        "                    \"description\": [\n" +
                        "                        \"완성된 키보드 설명\"\n" +
                        "                    ]\n" +
                        "                }\n"

                        , OpenAiChatOptions.builder()
                                .model("gpt-4o")
                                .temperature(0.8)
                                .build()
                ));


        String rawText = chatResponse.getResult().getOutput().getText();

        rawText = rawText.replaceAll("^```json\\n?", "")
                .replaceAll("\\n?```$", "");
        if (rawText.startsWith("\"") && rawText.endsWith("\"")) {
            rawText = rawText.substring(1, rawText.length() - 1);
        }
        rawText = rawText.replace("\\\"", "\"")
                .replace("\\n", "\n")
                .replace("\\t", "\t");
        //import Simple Json임 조심
        System.out.println(rawText);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(rawText);

        return new HttpResponse(HttpStatus.OK, ErrorCode.SUCCESS,jsonObject);
    }
}
