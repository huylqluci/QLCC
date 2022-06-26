package com.nhom43.quanlychungcubackendgradle.controller;

import com.nhom43.quanlychungcubackendgradle.dto.ABCDto;
import com.nhom43.quanlychungcubackendgradle.entity.ABC;
import com.nhom43.quanlychungcubackendgradle.mapper.ABCMapper;
import com.nhom43.quanlychungcubackendgradle.service.ABCService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RequestMapping("/aBC")
@RestController
public class ABCController {

    private final ABCService aBCService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated ABCDto aBCDto) {
        aBCService.save(aBCDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ABCDto> findById(@PathVariable("id") Long id) {
        ABCDto aBC = aBCService.findById(id);
        return ResponseEntity.ok(aBC);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        aBCService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<ABCDto>> pageQuery(ABCDto aBCDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ABCDto> aBCPage = aBCService.findByCondition(aBCDto, pageable);
        return ResponseEntity.ok(aBCPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated ABCDto aBCDto, @PathVariable("id") Long id) {
        aBCService.update(aBCDto, id);
        return ResponseEntity.ok().build();
    }
}