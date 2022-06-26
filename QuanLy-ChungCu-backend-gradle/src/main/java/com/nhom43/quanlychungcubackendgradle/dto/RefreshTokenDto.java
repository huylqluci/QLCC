package com.nhom43.quanlychungcubackendgradle.dto;

import com.nhom43.quanlychungcubackendgradle.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@NoArgsConstructor
public class RefreshTokenDto extends AbstractDto<Long> {

    private Long id;
    @Size(max = 255)
    private String token;
    private Instant createDate;
    private User user;

}