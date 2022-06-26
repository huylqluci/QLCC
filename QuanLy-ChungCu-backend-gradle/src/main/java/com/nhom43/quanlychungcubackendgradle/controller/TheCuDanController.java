package com.nhom43.quanlychungcubackendgradle.controller;

import com.nhom43.quanlychungcubackendgradle.dto.TheCuDanDto;
import com.nhom43.quanlychungcubackendgradle.service.TheCuDanService;
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
@RequestMapping("/api/the-cu-dan")
@RestController
public class TheCuDanController {

    private final TheCuDanService theCuDanService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated TheCuDanDto theCuDanDto) {
        theCuDanService.save(theCuDanDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheCuDanDto> findById(@PathVariable("id") Long id) {
        TheCuDanDto theCuDan = theCuDanService.findById(id);
        return ResponseEntity.ok(theCuDan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        theCuDanService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<TheCuDanDto>> pageQuery(TheCuDanDto theCuDanDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<TheCuDanDto> theCuDanPage = theCuDanService.findByCondition(theCuDanDto, pageable);
        return ResponseEntity.ok(theCuDanPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated TheCuDanDto theCuDanDto, @PathVariable("id") Long id) {
        theCuDanService.update(theCuDanDto, id);
        return ResponseEntity.ok().build();
    }

    // ------------------------------------------------------------------------------------------------------------- //

    @GetMapping("")
    public ResponseEntity<List<TheCuDanDto>> findAll() {
        List<TheCuDanDto> list = theCuDanService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/da-xoa")
    public ResponseEntity<List<TheCuDanDto>> findAllByDaXoa() {
        List<TheCuDanDto> list = theCuDanService.findAllByDaXoa(true);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/chua-xoa")
    public ResponseEntity<List<TheCuDanDto>> findAllByChuaXoa() {
        List<TheCuDanDto> list = theCuDanService.findAllByDaXoa(false);
        return ResponseEntity.ok(list);
    }
}