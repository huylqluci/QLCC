package com.nhom43.quanlychungcubackendgradle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DichVuCoDinhDto extends AbstractDto<Long> {

    private Long id;
    private String ten;
    private Double donGia;

}