package com.nhom43.quanlychungcubackendgradle.dto;

import com.nhom43.quanlychungcubackendgradle.entity.BoPhan;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoaiSuaChuaDto extends AbstractDto<Long> {

    private Long id;
    private String ten;
    private Double donGia;

    private BoPhan boPhan;
}