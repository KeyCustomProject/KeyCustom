package com.ho.edcustom.DTO.Request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ho.edcustom.DTO.KeyCaps;
import com.ho.edcustom.Deserializer.FlexibleInputDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {
    private String email;
    private String title;
    private String barebonecolor;
    private String keyboardtype;

    @JsonDeserialize(using = FlexibleInputDeserializer.class)
    private Object keycapcolor;

    private String switchcolor;
    //private String itemimage;
}
