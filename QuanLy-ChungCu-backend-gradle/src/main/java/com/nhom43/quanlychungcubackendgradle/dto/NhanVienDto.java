package com.nhom43.quanlychungcubackendgradle.dto;

import com.nhom43.quanlychungcubackendgradle.entity.BoPhan;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NhanVienDto extends AbstractDto<Long> {

    private Long id;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private BoPhan boPhan;

}