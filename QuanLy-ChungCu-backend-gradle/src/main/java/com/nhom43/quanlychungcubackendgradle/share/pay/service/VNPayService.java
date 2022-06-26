package com.nhom43.quanlychungcubackendgradle.share.pay.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@Transactional
public class VNPayService {


    public String getInfo(HttpServletRequest request) {
        return "kq";
    }
}
