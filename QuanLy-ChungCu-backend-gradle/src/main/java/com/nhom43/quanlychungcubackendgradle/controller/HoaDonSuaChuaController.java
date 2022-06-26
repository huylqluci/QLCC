package com.nhom43.quanlychungcubackendgradle.controller;

import com.nhom43.quanlychungcubackendgradle.dto.ChiTietHoaDonSuaChuaDto;
import com.nhom43.quanlychungcubackendgradle.dto.HoaDonSuaChuaDto;
import com.nhom43.quanlychungcubackendgradle.dto.request.HoaDonSuaChuaRequest;
import com.nhom43.quanlychungcubackendgradle.service.ChiTietHoaDonSuaChuaService;
import com.nhom43.quanlychungcubackendgradle.service.HoaDonSuaChuaService;
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
@RequestMapping("/api/hoa-don-sua-chua")
@RestController
public class HoaDonSuaChuaController {

    private final HoaDonSuaChuaService hoaDonSuaChuaService;
    private final ChiTietHoaDonSuaChuaService chiTietHoaDonSuaChuaService;

    @PostMapping
    public ResponseEntity<HoaDonSuaChuaDto> save(@RequestBody @Validated HoaDonSuaChuaDto hoaDonSuaChuaDto) {
        HoaDonSuaChuaDto hoaDonSuaChuaDto1 = hoaDonSuaChuaService.save(hoaDonSuaChuaDto);
        return ResponseEntity.ok(hoaDonSuaChuaDto1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoaDonSuaChuaDto> findById(@PathVariable("id") Long id) {
        HoaDonSuaChuaDto hoaDonSuaChua = hoaDonSuaChuaService.findById(id);
        return ResponseEntity.ok(hoaDonSuaChua);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        hoaDonSuaChuaService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<HoaDonSuaChuaDto>> pageQuery(HoaDonSuaChuaDto hoaDonSuaChuaDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<HoaDonSuaChuaDto> hoaDonSuaChuaPage = hoaDonSuaChuaService.findByCondition(hoaDonSuaChuaDto, pageable);
        return ResponseEntity.ok(hoaDonSuaChuaPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated HoaDonSuaChuaRequest hoaDonSuaChuaRequest, @PathVariable("id") Long id) {
        hoaDonSuaChuaService.update(hoaDonSuaChuaRequest, id);
        return ResponseEntity.ok().build();
    }

    // ------------------------------------------------------------------------------------------------------------- //

    @GetMapping("")
    public ResponseEntity<List<HoaDonSuaChuaDto>> findAll() {
        List<HoaDonSuaChuaDto> list = hoaDonSuaChuaService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/da-thanh-toan")
    public ResponseEntity<List<HoaDonSuaChuaDto>> findAllByDaThanhToan() {
        List<HoaDonSuaChuaDto> list = hoaDonSuaChuaService.findAllByTrangThai(true);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/chua-thanh-toan")
    public ResponseEntity<List<HoaDonSuaChuaDto>> findAllByChuaThanhToan() {
        List<HoaDonSuaChuaDto> list = hoaDonSuaChuaService.findAllByTrangThai(false);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/chi-tiet-hoa-don-dich-vu")
    public ResponseEntity<?> findAllByHoaDonSuaChua_Id(@PathVariable("id") Long id) {
        List<ChiTietHoaDonSuaChuaDto> list = chiTietHoaDonSuaChuaService.findByHoaDonSuaChua_Id(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/da-thanh-toan/nam={nam}&thang={thang}")
    public ResponseEntity<List<HoaDonSuaChuaDto>> findAllByDaThanhToanTrongThang
            (@PathVariable("nam") String nam,
             @PathVariable("thang") String thang) {
        List<HoaDonSuaChuaDto> list = hoaDonSuaChuaService.findAllByTrangThaiTrongThang
                (true, nam, thang);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/chua-thanh-toan/nam={nam}&thang={thang}")
    public ResponseEntity<List<HoaDonSuaChuaDto>> findAllByChuaThanhToanTrongThang
            (@PathVariable("nam") String nam,
             @PathVariable("thang") String thang) {
        List<HoaDonSuaChuaDto> list = hoaDonSuaChuaService.findAllByTrangThaiTrongThang
                (false, nam, thang);
        return ResponseEntity.ok(list);
    }
}