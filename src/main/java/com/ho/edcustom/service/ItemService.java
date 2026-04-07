package com.ho.edcustom.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ho.edcustom.DTO.KeyCaps;
import com.ho.edcustom.DTO.Response.HttpResponse;
import com.ho.edcustom.entity.Item;
import com.ho.edcustom.enumSet.ErrorCode;
import com.ho.edcustom.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.K;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final FireBaseService fireBaseService;
    public HttpResponse saveItem(String email, String title, String barebonecolor, String keyboardtype
            , Object keycapcolor,String switchcolor, MultipartFile multipartFile) throws IOException {

        if (Stream.of(email, barebonecolor, keyboardtype, switchcolor)
                .anyMatch(str -> str == null || str.isBlank())) {
            return new HttpResponse(HttpStatus.BAD_REQUEST, ErrorCode.ITEM_BAD_REQUEST,null);
        }
        String imageUrl =fireBaseService.uploadItem(multipartFile);


        KeyCaps keyCaps = new KeyCaps();

        if (keycapcolor instanceof String) {
            String strColors = (String) keycapcolor;
            keyCaps.setAllColors(strColors);

        }
        else if(keycapcolor instanceof Map)
        {
            Map<?, ?> rawMap = (Map<?, ?>) keycapcolor;
            Map<String, String> keyColors = new HashMap<>();
            for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
                keyColors.put(entry.getKey().toString(), entry.getValue().toString());
            }

            for (Map.Entry<String, String> entry : keyColors.entrySet()) {
                keyCaps.setColor(entry.getKey(), entry.getValue());
            }
        }


        itemRepository.save(Item.builder()
                .email(email)
                .title(title)
                .barebonecolor(barebonecolor)
                .keyboardtype(keyboardtype)
                .keycapcolor(keyCaps)
                .switchcolor(switchcolor)
                .imageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .createdBy(email)
                .build());

        return new HttpResponse(HttpStatus.CREATED, ErrorCode.CREATED,null);
    }


    public HttpResponse findcustomitem(String email)
    {
        if (itemRepository.findItemByEmail(email).isEmpty()) {
            return new HttpResponse(HttpStatus.NOT_FOUND,ErrorCode.CUSTOM_NOT_FOUND,null);
            //현재 not found만 있는데 id가 틀릴경우도 제어해야함.
        }
        List<Item> list =itemRepository.findItemByEmail(email);
        return new HttpResponse(HttpStatus.OK,ErrorCode.SUCCESS,list);
    }

}
