package com.nhom43.quanlychungcubackendgradle.share.pay.dto;

import com.nhom43.quanlychungcubackendgradle.entity.CanHo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class HoaDonDichVuResponse {
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
