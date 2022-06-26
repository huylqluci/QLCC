package com.nhom43.quanlychungcubackendgradle.controller;

import com.nhom43.quanlychungcubackendgradle.dto.DichVuCoDinhDto;
import com.nhom43.quanlychungcubackendgradle.service.DichVuCoDinhService;
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
@RequestMapping("/api/dich-vu-co-dinh")
@RestController
public class DichVuCoDinhController {

    private final DichVuCoDinhService dichVuCoDinhService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated DichVuCoDinhDto dichVuCoDinhDto) {
        dichVuCoDinhService.save(dichVuCoDinhDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DichVuCoDinhDto> findById(@PathVariable("id") Long id) {
        DichVuCoDinhDto dichVuCoDinh = dichVuCoDinhService.findById(id);
        return ResponseEntity.ok(dichVuCoDinh);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        dichVuCoDinhService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<DichVuCoDinhDto>> pageQuery(DichVuCoDinhDto dichVuCoDinhDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<DichVuCoDinhDto> dichVuCoDinhPage = dichVuCoDinhService.findByCondition(dichVuCoDinhDto, pageable);
        return ResponseEntity.ok(dichVuCoDinhPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated DichVuCoDinhDto dichVuCoDinhDto, @PathVariable("id") Long id) {
        dichVuCoDinhService.update(dichVuCoDinhDto, id);
        return ResponseEntity.ok().build();
    }


    // ------------------------------------------------------------------------------------------------------------- //


    @GetMapping("")
    public ResponseEntity<List<DichVuCoDinhDto>> findAll() {
        List<DichVuCoDinhDto> list = dichVuCoDinhService.findAll();
        return ResponseEntity.ok(list);
    }
}