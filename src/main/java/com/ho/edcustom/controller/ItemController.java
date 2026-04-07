package com.ho.edcustom.controller;

import com.ho.edcustom.DTO.Request.EmailRequest;
import com.ho.edcustom.DTO.Request.ItemRequest;
import com.ho.edcustom.DTO.Response.HttpResponse;
import com.ho.edcustom.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @PostMapping(value ="/items/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpResponse> itemSave(
            @RequestPart ItemRequest DTO,
            @RequestPart(value = "file",required = false) MultipartFile multipartFile) throws IOException {
        HttpResponse Response =itemService.saveItem
                (DTO.getEmail(),
                 DTO.getTitle(),
                 DTO.getBarebonecolor(),
                 DTO.getKeyboardtype(),
                 DTO.getKeycapcolor(),
                 DTO.getSwitchcolor(),
                        multipartFile);
        return new ResponseEntity<>(Response,Response.getStatus());
    }
    @PostMapping("/items/find")
    public ResponseEntity<HttpResponse> itemFind(@RequestBody EmailRequest DTO)
    {
        HttpResponse Response =itemService.findcustomitem(DTO.getEmail());
        return new ResponseEntity<>(Response,Response.getStatus());
    }

}
