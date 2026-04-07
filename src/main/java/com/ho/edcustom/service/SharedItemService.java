package com.ho.edcustom.service;


import com.ho.edcustom.DTO.KeyCaps;
import com.ho.edcustom.DTO.Response.HttpResponse;
import com.ho.edcustom.entity.SharedItem;
import com.ho.edcustom.enumSet.ErrorCode;
import com.ho.edcustom.repository.SharedItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SharedItemService {
    private final SharedItemRepository sharedItemRepository;
    private final FireBaseService fireBaseService;
    public HttpResponse saveItem(String email, String title, String barebonecolor, String keyboardtype, Map<String, String> keycapcolor, String switchcolor, String imageUrl){

        if (Stream.of(email, barebonecolor, keyboardtype, switchcolor)
                .anyMatch(str -> str == null || str.isBlank())) {
            return new HttpResponse(HttpStatus.BAD_REQUEST, ErrorCode.ITEM_BAD_REQUEST, null);
        }
        KeyCaps keyCaps = new KeyCaps();
        for (Map.Entry<String, String> entry : keycapcolor.entrySet()) {
            keyCaps.setColor(entry.getKey(), entry.getValue());
        }


        sharedItemRepository.save(SharedItem.builder()
                .email(email)
                .title(title)
                .barebonecolor(barebonecolor)
                .keyboardtype(keyboardtype)
                .keycapcolor(keyCaps)
                .switchcolor(switchcolor)
                .imageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .sharedBy(email)
                .likes(0)
                .build());

        return new HttpResponse(HttpStatus.CREATED, ErrorCode.CREATED,null);
    }

    public HttpResponse findItem()
    {
        if (sharedItemRepository.findAll().isEmpty()) {
            return new HttpResponse(HttpStatus.NOT_FOUND,ErrorCode.CUSTOM_NOT_FOUND,null);
            //현재 not found만 있는데 id가 틀릴경우도 제어해야함.
        }
        List<SharedItem> list =sharedItemRepository.findAll();
        return new HttpResponse(HttpStatus.OK,ErrorCode.SUCCESS,list);
    }
}
