package com.nhom43.quanlychungcubackendgradle.share.pay.controller;

import com.nhom43.quanlychungcubackendgradle.dto.ABCDto;
import com.nhom43.quanlychungcubackendgradle.service.ABCService;
import com.nhom43.quanlychungcubackendgradle.share.pay.service.VNPayService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@RequestMapping("/api/vnpay")
@RestController
public class VNPayController {

    private final VNPayService vNPayService;

    @GetMapping("/")
    public ResponseEntity<?> getInfo(HttpServletRequest request) {
        String kq = vNPayService.getInfo(request);
        return ResponseEntity.ok(kq);
    }

}