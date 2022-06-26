package com.nhom43.quanlychungcubackendgradle.share.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {

    @NotBlank
    private String refreshToken;

    @NotBlank
    private String username;

}