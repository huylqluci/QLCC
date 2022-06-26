package com.nhom43.quanlychungcubackendgradle.share.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccoutEditRequest {

    private String username;

    private String password;

    private Boolean status;

}
