package com.nhom43.quanlychungcubackendgradle.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {

    private int soDanCuTuoiNhoHon18;
    private int soDanCuTuoiTu18Den60;
    private int soDanCuTuoiLonHon60;

    private int soDanCuTuoiNhoHon18_CoSinhNhatTrongThang;

    private int soHoaDonThangTruocDaThanhToan;
    private int soHoaDonThangTruocChuaThanhToan;

    private int soHoaDonSuaChuaThangTruocDaThanhToan;
    private int soHoaDonSuaChuaThangTruocChuaThanhToan;
}
