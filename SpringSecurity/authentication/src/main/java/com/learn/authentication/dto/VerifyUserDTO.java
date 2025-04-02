package com.learn.authentication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUserDTO {

    private String email;
    private String verificationCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
