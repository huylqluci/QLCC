package com.nhom43.quanlychungcubackendgradle.dto;

import com.nhom43.quanlychungcubackendgradle.entity.CanHo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class HoaDonDichVuDto extends AbstractDto<Long> {

    private Long id;
    private LocalDateTime ngayTao;
    private Boolean trangThai;
    private CanHo canHo;

    private String tenNguoiThanhToan;
    private String soDienThoai;
    private String loaiHinhThanhToan;
    private LocalDateTime ngayThanhToan;

    private double soTien;

}