package com.nhom43.quanlychungcubackendgradle.controller;

import com.nhom43.quanlychungcubackendgradle.dto.LoaiSuaChuaDto;
import com.nhom43.quanlychungcubackendgradle.dto.NhanVienDto;
import com.nhom43.quanlychungcubackendgradle.service.NhanVienService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/nhan-vien")
@RestController
public class NhanVienController {

    private final NhanVienService nhanVienService;

   @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated NhanVienDto nhanVienDto) {
        nhanVienService.save(nhanVienDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhanVienDto> findById(@PathVariable("id") Long id) {
        NhanVienDto nhanVien = nhanVienService.findById(id);
        return ResponseEntity.ok(nhanVien);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        nhanVienService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<NhanVienDto>> pageQuery(NhanVienDto nhanVienDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NhanVienDto> nhanVienPage = nhanVienService.findByCondition(nhanVienDto, pageable);
        return ResponseEntity.ok(nhanVienPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated NhanVienDto nhanVienDto, @PathVariable("id") Long id) {
        nhanVienService.update(nhanVienDto, id);
        return ResponseEntity.ok().build();
    }


    // ------------------------------------------------------------------------------------------------------------- //


    @GetMapping("")
    public ResponseEntity<List<NhanVienDto>> findAll() {
        List<NhanVienDto> list = nhanVienService.findAll();
        return ResponseEntity.ok(list);
    }
}