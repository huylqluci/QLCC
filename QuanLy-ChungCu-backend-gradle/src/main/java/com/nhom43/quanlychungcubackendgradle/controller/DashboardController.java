package com.nhom43.quanlychungcubackendgradle.controller;

import com.nhom43.quanlychungcubackendgradle.dto.CuDanDto;
import com.nhom43.quanlychungcubackendgradle.dto.response.CountHoaDonResponse;
import com.nhom43.quanlychungcubackendgradle.dto.response.DashboardResponse;
import com.nhom43.quanlychungcubackendgradle.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/dashboard")
@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/admin")
    public ResponseEntity<?> thongKe() {
        DashboardResponse dashboardResponse = dashboardService.thongKe();
        return ResponseEntity.ok(dashboardResponse);
    }

    @GetMapping("/admin/cu-dan-sinh-nhat-trong-thang")
    public ResponseEntity<?> findAllBySinhNhatThangNay() {
        List<CuDanDto> list = dashboardService.findAllBySinhNhatThangNay();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/admin/gui-loi-chuc-mung-sinh-nhat/{id}")
    public ResponseEntity<?> guiLoiChucMungSinhNhat(@PathVariable("id") Long id) {
        String kq = "đã gửi lời chúc mừng sinh nhật";
        dashboardService.guiLoiChucMungSinhNhat(id);
        return ResponseEntity.ok().build();
//        return ResponseEntity.ok(kq);
    }

    @GetMapping("/admin/thong-ke-hoa-don-dich-vu/nam={nam}&thang={thang}")
    public ResponseEntity<?> thongKeHoaDonDichVu(@PathVariable("nam") String nam,
                                                       @PathVariable("thang") String thang) {
        CountHoaDonResponse countHoaDonResponse = dashboardService.thongKeHoaDonDichVu(nam, thang);
        return ResponseEntity.ok(countHoaDonResponse);
    }

    @GetMapping("/admin/thong-ke-hoa-don-sua-chua/nam={nam}&thang={thang}")
    public ResponseEntity<?> thongKeHoaDonSuaChua(@PathVariable("nam") String nam,
                                                 @PathVariable("thang") String thang) {
        CountHoaDonResponse countHoaDonResponse = dashboardService.thongKeHoaDonSuaChua(nam, thang);
        return ResponseEntity.ok(countHoaDonResponse);
    }

//    @GetMapping("/user")
//    public ResponseEntity<?> thongKeUser() {
//        DashboardResponse dashboardResponse = dashboardService.thongKe();
//        return ResponseEntity.ok(dashboardResponse);
//    }

}