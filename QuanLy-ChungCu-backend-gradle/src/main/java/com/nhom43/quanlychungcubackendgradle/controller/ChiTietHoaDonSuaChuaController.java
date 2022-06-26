package com.nhom43.quanlychungcubackendgradle.controller;

import com.nhom43.quanlychungcubackendgradle.dto.ChiTietHoaDonSuaChuaDto;
import com.nhom43.quanlychungcubackendgradle.service.ChiTietHoaDonSuaChuaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RequestMapping("/api/chi-tiet-hoa-don-sua-chua")
@RestController
public class ChiTietHoaDonSuaChuaController {

    private final ChiTietHoaDonSuaChuaService chiTietHoaDonSuaChuaService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated ChiTietHoaDonSuaChuaDto chiTietHoaDonSuaChuaDto) {
        chiTietHoaDonSuaChuaService.save(chiTietHoaDonSuaChuaDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietHoaDonSuaChuaDto> findById(@PathVariable("id") Long id) {
        ChiTietHoaDonSuaChuaDto chiTietHoaDonSuaChua = chiTietHoaDonSuaChuaService.findById(id);
        return ResponseEntity.ok(chiTietHoaDonSuaChua);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        chiTietHoaDonSuaChuaService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<ChiTietHoaDonSuaChuaDto>> pageQuery(ChiTietHoaDonSuaChuaDto chiTietHoaDonSuaChuaDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ChiTietHoaDonSuaChuaDto> chiTietHoaDonSuaChuaPage = chiTietHoaDonSuaChuaService.findByCondition(chiTietHoaDonSuaChuaDto, pageable);
        return ResponseEntity.ok(chiTietHoaDonSuaChuaPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated ChiTietHoaDonSuaChuaDto chiTietHoaDonSuaChuaDto, @PathVariable("id") Long id) {
        chiTietHoaDonSuaChuaService.update(chiTietHoaDonSuaChuaDto, id);
        return ResponseEntity.ok().build();
    }
}