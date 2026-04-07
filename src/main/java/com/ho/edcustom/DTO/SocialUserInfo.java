package com.ho.edcustom.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SocialUserInfo {
    private Long id;
    private String nickname;
    private String email;

}