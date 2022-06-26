package com.nhom43.quanlychungcubackendgradle.controller;

import com.nhom43.quanlychungcubackendgradle.dto.ChiTietHoaDonDichVuDto;
import com.nhom43.quanlychungcubackendgradle.service.ChiTietHoaDonDichVuService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RequestMapping("/api/chi-tiet-hoa-don-dich-vu")
@RestController
public class ChiTietHoaDonDichVuController {

    private final ChiTietHoaDonDichVuService chiTietHoaDonDichVuService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated ChiTietHoaDonDichVuDto chiTietHoaDonDichVuDto) {
        chiTietHoaDonDichVuService.save(chiTietHoaDonDichVuDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietHoaDonDichVuDto> findById(@PathVariable("id") Long id) {
        ChiTietHoaDonDichVuDto chiTietHoaDonDichVu = chiTietHoaDonDichVuService.findById(id);
        return ResponseEntity.ok(chiTietHoaDonDichVu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        chiTietHoaDonDichVuService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<ChiTietHoaDonDichVuDto>> pageQuery(ChiTietHoaDonDichVuDto chiTietHoaDonDichVuDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ChiTietHoaDonDichVuDto> chiTietHoaDonDichVuPage = chiTietHoaDonDichVuService.findByCondition(chiTietHoaDonDichVuDto, pageable);
        return ResponseEntity.ok(chiTietHoaDonDichVuPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated ChiTietHoaDonDichVuDto chiTietHoaDonDichVuDto, @PathVariable("id") Long id) {
        chiTietHoaDonDichVuService.update(chiTietHoaDonDichVuDto, id);
        return ResponseEntity.ok().build();
    }
}