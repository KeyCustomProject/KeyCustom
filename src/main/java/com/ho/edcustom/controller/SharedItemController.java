package com.ho.edcustom.controller;

import com.ho.edcustom.DTO.Request.ItemRequest;
import com.ho.edcustom.DTO.Request.SitemRequest;
import com.ho.edcustom.DTO.Response.HttpResponse;
import com.ho.edcustom.service.SharedItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class SharedItemController {
    private final SharedItemService sharedItemService;
    @PostMapping(value = "/shareditems/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpResponse> sharedItemSave(@RequestPart SitemRequest DTO){
        HttpResponse Response =sharedItemService.saveItem
                (DTO.getEmail(),
                 DTO.getTitle(),
                 DTO.getBarebonecolor(),
                 DTO.getKeyboardtype(),
                 DTO.getKeycapcolor(),
                 DTO.getSwitchcolor(),
                 DTO.getImageUrl());
        return new ResponseEntity<>(Response,Response.getStatus());
    }
    @GetMapping("/shareditems/find")
    public ResponseEntity<HttpResponse> itemFind()
    {
        HttpResponse Response =sharedItemService.findItem();
        return new ResponseEntity<>(Response,Response.getStatus());
    }

}
