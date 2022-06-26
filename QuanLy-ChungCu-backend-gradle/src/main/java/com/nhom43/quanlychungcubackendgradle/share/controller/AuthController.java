package com.nhom43.quanlychungcubackendgradle.share.controller;

import com.nhom43.quanlychungcubackendgradle.share.dto.request.*;
import com.nhom43.quanlychungcubackendgradle.share.dto.response.AuthenticationResponse;
import com.nhom43.quanlychungcubackendgradle.share.service.AuthService;
import com.nhom43.quanlychungcubackendgradle.share.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/auth")
@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return new ResponseEntity<>("Tạo tài khoản thành công! Vui lòng kích hoạt qua địa chỉ email đăng kí!",
                OK);
    }

    @PostMapping("/register2")
    public ResponseEntity<String> register2(@RequestBody RegisterRequest2 registerRequest) {
        authService.register2(registerRequest);
        return new ResponseEntity<>("Tạo tài khoản thành công! Vui lòng kích hoạt qua địa chỉ email đăng kí!",
                OK);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody EmailRequest emailRequest) {
        authService.forgotPassword(emailRequest);
        return new ResponseEntity<>("Thành công gửi mã lấy lại mật khẩu",
                OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Tài khoản kích hoạt thành công", OK);
    }

    @GetMapping("/passwordVerification/{token}")
    public ResponseEntity<String> verifyPassword(@PathVariable String token) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.verifyPassword(token));
    }

    @PostMapping("/editPassword")
    public ResponseEntity<String> editPassword(@RequestBody AccoutEditRequest accoutEditRequest) {
        authService.editPassword(accoutEditRequest);
        return new ResponseEntity<>("Đã sửa mật khẩu mới", OK);
    }

    @PostMapping("/editStatus")
    public ResponseEntity<String> editStatus(@RequestBody AccoutEditRequest accoutEditRequest) {
        authService.editStatus(accoutEditRequest);
        return ResponseEntity.ok().build();
//        return new ResponseEntity<>("Đã thay đổi thành đã xoá", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Đã làm mới mã JWT !!");
    }


}
