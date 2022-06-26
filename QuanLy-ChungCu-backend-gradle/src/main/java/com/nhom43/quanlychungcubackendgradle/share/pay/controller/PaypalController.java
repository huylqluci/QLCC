package com.nhom43.quanlychungcubackendgradle.share.pay.controller;

import com.nhom43.quanlychungcubackendgradle.dto.HoaDonDichVuDto;
import com.nhom43.quanlychungcubackendgradle.dto.HoaDonSuaChuaDto;
import com.nhom43.quanlychungcubackendgradle.share.pay.service.PaypalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PaypalController {

    private final PaypalService paypalService;

    @GetMapping("/hoa-don-dich-vu/paypal/{id}")
    public ResponseEntity<HoaDonDichVuDto> paypalThanhToan_HDDV(@PathVariable Long id) {
        return status(HttpStatus.OK).body(paypalService.kiemTraTrangThaiThanhToan_HDDV(id));
    }


    @GetMapping("/hoa-don-sua-chua/paypal/{id}")
    public ResponseEntity<HoaDonSuaChuaDto> paypalThanhToan_HDSC(@PathVariable Long id) {
        return status(HttpStatus.OK).body(paypalService.kiemTraTrangThaiThanhToan_HDSC(id));
    }

    @GetMapping("/hoa-don-dich-vu/submitpaypal/{id}")
    public ResponseEntity<String> submitPaypal_HDDV(@PathVariable Long id) {
        paypalService.submitThanhToan_HDDV(id);
        return new ResponseEntity<>("Bạn đã thanh toán thành công hóa đơn: " + id, OK);
    }

    @GetMapping("/hoa-don-sua-chua/submitpaypal/{id}")
    public ResponseEntity<String> submitPaypal_HDSC(@PathVariable Long id) {
        paypalService.submitThanhToan_HDSC(id);
        return new ResponseEntity<>("Bạn đã thanh toán thành công hóa đơn: " + id, OK);
    }

}
