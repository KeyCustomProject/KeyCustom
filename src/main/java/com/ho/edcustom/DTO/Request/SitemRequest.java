package com.ho.edcustom.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SitemRequest {
    private String email;
    private String title;
    private String barebonecolor;
    private String keyboardtype;
    private Map<String, String> keycapcolor;
    private String switchcolor;
    private String imageUrl;
}
