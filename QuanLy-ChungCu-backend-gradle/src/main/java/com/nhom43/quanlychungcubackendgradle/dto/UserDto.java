package com.nhom43.quanlychungcubackendgradle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto extends AbstractDto<Long> {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Instant created;
    private boolean enabled;
    private boolean status;
    private String image;
    private String role;

}