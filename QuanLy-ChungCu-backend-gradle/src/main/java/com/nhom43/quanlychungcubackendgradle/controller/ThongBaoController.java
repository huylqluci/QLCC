package com.nhom43.quanlychungcubackendgradle.controller;

import com.nhom43.quanlychungcubackendgradle.dto.ThongBaoDto;
import com.nhom43.quanlychungcubackendgradle.service.ThongBaoService;
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
@RequestMapping("/api/thong-bao")
@RestController
public class ThongBaoController {

    private final ThongBaoService thongBaoService;

  @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated ThongBaoDto thongBaoDto) {
        thongBaoService.save(thongBaoDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThongBaoDto> findById(@PathVariable("id") Long id) {
        ThongBaoDto thongBao = thongBaoService.findById(id);
        return ResponseEntity.ok(thongBao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        thongBaoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<ThongBaoDto>> pageQuery(ThongBaoDto thongBaoDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ThongBaoDto> thongBaoPage = thongBaoService.findByCondition(thongBaoDto, pageable);
        return ResponseEntity.ok(thongBaoPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated ThongBaoDto thongBaoDto, @PathVariable("id") Long id) {
        thongBaoService.update(thongBaoDto, id);
        return ResponseEntity.ok().build();
    }

    // ------------------------------------------------------------------------------------------------------------- //

    @GetMapping("")
    public ResponseEntity<List<ThongBaoDto>> findAll() {
        List<ThongBaoDto> list = thongBaoService.findAll();
        return ResponseEntity.ok(list);
    }
}