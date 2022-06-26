package com.nhom43.quanlychungcubackendgradle.controller;

import com.nhom43.quanlychungcubackendgradle.dto.BoPhanDto;
import com.nhom43.quanlychungcubackendgradle.dto.LoaiSuaChuaDto;
import com.nhom43.quanlychungcubackendgradle.dto.NhanVienDto;
import com.nhom43.quanlychungcubackendgradle.service.BoPhanService;
import com.nhom43.quanlychungcubackendgradle.service.LoaiSuaChuaService;
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
@RequestMapping("/api/bo-phan")
@RestController
public class BoPhanController {

    private final BoPhanService boPhanService;
    private final NhanVienService nhanVienService;
    private final LoaiSuaChuaService loaiSuaChuaService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated BoPhanDto boPhanDto) {
        boPhanService.save(boPhanDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoPhanDto> findById(@PathVariable("id") Long id) {
        BoPhanDto boPhan = boPhanService.findById(id);
        return ResponseEntity.ok(boPhan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        boPhanService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<BoPhanDto>> pageQuery(BoPhanDto boPhanDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoPhanDto> boPhanPage = boPhanService.findByCondition(boPhanDto, pageable);
        return ResponseEntity.ok(boPhanPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated BoPhanDto boPhanDto, @PathVariable("id") Long id) {
        boPhanService.update(boPhanDto, id);
        return ResponseEntity.ok().build();
    }

    // ------------------------------------------------------------------------------------------------------------- //


    @GetMapping("")
    public ResponseEntity<List<BoPhanDto>> findAll() {
        List<BoPhanDto> list = boPhanService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/nhan-vien")
    public ResponseEntity<List<NhanVienDto>> findAllNhanVienByBoPhan(@PathVariable ("id") Long id) {
        List<NhanVienDto> list = nhanVienService.findAllByBoPhan(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/loai-sua-chua")
    public ResponseEntity<List<LoaiSuaChuaDto>> findAllLoaiSuaChuaByBoPhan(@PathVariable ("id") Long id) {
        List<LoaiSuaChuaDto> list = loaiSuaChuaService.findAllByBoPhan(id);
        return ResponseEntity.ok(list);
    }
}