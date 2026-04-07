package com.ho.edcustom.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequest {
    private String email;
    private String currentPassword;
    private String newPassword;
}
