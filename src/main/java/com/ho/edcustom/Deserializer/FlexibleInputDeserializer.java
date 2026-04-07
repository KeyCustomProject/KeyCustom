package com.ho.edcustom.Deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class FlexibleInputDeserializer extends JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (node.isTextual()) {
            return node.asText(); // 문자열
        } else if (node.isObject()) {
            // JSON 객체 -> Map<String, Object>로 변환
            ObjectMapper mapper = (ObjectMapper) p.getCodec();
            return mapper.convertValue(node, new TypeReference<Map<String, Object>>() {});
        } else {
            return null; // 또는 예외 처리
        }
    }
}