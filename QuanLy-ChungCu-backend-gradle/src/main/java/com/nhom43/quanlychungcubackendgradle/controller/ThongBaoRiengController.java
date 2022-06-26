package com.nhom43.quanlychungcubackendgradle.controller;

import com.nhom43.quanlychungcubackendgradle.dto.ThongBaoRiengDto;
import com.nhom43.quanlychungcubackendgradle.service.ThongBaoRiengService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/thong-bao-rieng")
@RestController
public class ThongBaoRiengController {
    private final ThongBaoRiengService thongBaoRiengService;

    public ThongBaoRiengController(ThongBaoRiengService thongBaoRiengService) {
        this.thongBaoRiengService = thongBaoRiengService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated ThongBaoRiengDto thongBaoRiengDto) {
        thongBaoRiengService.save(thongBaoRiengDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThongBaoRiengDto> findById(@PathVariable("id") Long id) {
        ThongBaoRiengDto thongBaoRieng = thongBaoRiengService.findById(id);
        return ResponseEntity.ok(thongBaoRieng);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        thongBaoRiengService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<ThongBaoRiengDto>> pageQuery(ThongBaoRiengDto thongBaoRiengDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ThongBaoRiengDto> thongBaoRiengPage = thongBaoRiengService.findByCondition(thongBaoRiengDto, pageable);
        return ResponseEntity.ok(thongBaoRiengPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated ThongBaoRiengDto thongBaoRiengDto, @PathVariable("id") Long id) {
        thongBaoRiengService.update(thongBaoRiengDto, id);
        return ResponseEntity.ok().build();
    }

    // ------------------------------------------------------------------------------------------------------------- //

    @GetMapping("")
    public ResponseEntity<List<ThongBaoRiengDto>> findAll() {
        List<ThongBaoRiengDto> list = thongBaoRiengService.findAll();
        return ResponseEntity.ok(list);
    }
}