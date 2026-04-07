package com.ho.edcustom.DTO.Response;

import com.ho.edcustom.enumSet.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class HttpResponse {

    private final HttpStatus status;
    private final int code;
    private final String message;
    private final Object data;

    public HttpResponse(HttpStatus status,ErrorCode code, Object data){
        this.status=status;
        this.code=code.getCode();
        this.message=code.getMessage();
        this.data=data;

    }


}
