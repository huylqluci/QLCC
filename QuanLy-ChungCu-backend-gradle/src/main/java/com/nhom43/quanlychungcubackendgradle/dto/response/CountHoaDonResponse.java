package com.nhom43.quanlychungcubackendgradle.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountHoaDonResponse {
    private int soHoaDonChuaThanhToan;
    private int soHoaDonDaThanhToan;
}
