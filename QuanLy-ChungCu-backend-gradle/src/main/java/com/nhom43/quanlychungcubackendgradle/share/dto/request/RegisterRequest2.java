package com.nhom43.quanlychungcubackendgradle.share.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest2 {

    private String email;

    private String username;

    private String password;

    private String image;

    private String role;

    private Long idCanHo;

}
