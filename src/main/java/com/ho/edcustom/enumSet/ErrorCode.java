package com.ho.edcustom.enumSet;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS(200, "요청을 성공적으로 처리했습니다."),
    LIKE_INSERT(200,"좋아요가 반영되었습니다."),
    LIKE_DELETE(200,"좋아요가 취소되었습니다."),
    CREATED(201,"생성되었습니다."),
    BAD_REQUEST(400, "잘못된 요청입니다."),
    LOGIN_BAD_REQUEST(400,"아이디 또는 비밀번호가 틀렸습니다."),
    BAD_REQUEST_DUPLICATION(400,"이미 사용된 아이디 입니다."),
    NICKNAME_DUPLICATION(400,"이미 사용된 닉네임 입니다."),
    ITEM_BAD_REQUEST(400,"내용이 누락되었습니다."),
    UNAUTHORIZED(401, "권한이 없습니다."),
    NOT_FOUND(404,"찾을 수 없습니다."),
    MEMBER_NOT_FOUND(404,"멤버를 찾을 수 없습니다."),
    ITEM_NOT_FOUND(404,"공유된 키보드를 찾을 수 없습니다."),
    CUSTOM_NOT_FOUND(404,"저장된 커스텀이 없습니다."),
    INTERNAL_ERROR(500, "서버에 에러가 발생했습니다.");


    private final int code;
    private final String message;

    ErrorCode(int code,String message)
    {
        this.code=code;
        this.message=message;
    }

}
