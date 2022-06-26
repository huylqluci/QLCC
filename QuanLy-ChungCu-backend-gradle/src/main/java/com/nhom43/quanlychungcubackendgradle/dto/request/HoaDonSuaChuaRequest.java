package com.nhom43.quanlychungcubackendgradle.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonSuaChuaRequest {

    private String tenNguoiThanhToan;
    private String soDienThoai;
    private String loaiHinhThanhToan;
    private LocalDateTime ngayThanhToan;
}
