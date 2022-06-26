package com.nhom43.quanlychungcubackendgradle.dto;

import com.nhom43.quanlychungcubackendgradle.entity.DichVuCoDinh;
import com.nhom43.quanlychungcubackendgradle.entity.HoaDonDichVu;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChiTietHoaDonDichVuDto extends AbstractDto<Long> {

    private Long id;
    private Double donGia;
    private Integer soLuong;
    private String moTa;
    private DichVuCoDinh dichVuCoDinh;
    private HoaDonDichVu hoaDonDichVu;

}