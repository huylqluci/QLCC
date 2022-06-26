package com.nhom43.quanlychungcubackendgradle.share.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String image;

    private String role;

    private String created;

    private boolean enabled;

    private boolean status;


}
