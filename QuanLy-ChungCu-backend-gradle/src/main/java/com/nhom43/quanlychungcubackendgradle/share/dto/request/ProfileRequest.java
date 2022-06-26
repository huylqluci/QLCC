package com.nhom43.quanlychungcubackendgradle.share.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {

    private Long id;

    private String username;

    private String password;

    private String passwordEdit;

    private String email;

    private String identityCard;

    private String image;

    private String role;

    private Instant createdDate;

    private boolean enabled;

    private boolean status;

}
