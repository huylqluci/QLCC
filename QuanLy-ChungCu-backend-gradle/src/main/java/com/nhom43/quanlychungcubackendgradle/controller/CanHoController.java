package com.nhom43.quanlychungcubackendgradle.controller;

import com.nhom43.quanlychungcubackendgradle.dto.*;
import com.nhom43.quanlychungcubackendgradle.service.*;
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
@RequestMapping("/api/can-ho")
@RestController
public class CanHoController {

    private final CanHoService canHoService;
    private final PhuongTienService phuongTienService;
    private final TheCuDanService theCuDanService;
    private final CuDanService cuDanService;
    private final HoaDonDichVuService hoaDonDichVuService;
    private final HoaDonSuaChuaService hoaDonSuaChuaService;
    private final ThongBaoRiengService thongBaoRiengService;

    @PostMapping
    public ResponseEntity<CanHoDto> save(@RequestBody @Validated CanHoDto canHoDto) {
        canHoService.save(canHoDto);
        return ResponseEntity.ok(canHoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanHoDto> findById(@PathVariable("id") Long id) {
        CanHoDto canHo = canHoService.findById(id);
        return ResponseEntity.ok(canHo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        canHoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<CanHoDto>> pageQuery(CanHoDto canHoDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CanHoDto> canHoPage = canHoService.findByCondition(canHoDto, pageable);
        return ResponseEntity.ok(canHoPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated CanHoDto canHoDto, @PathVariable("id") Long id) {
        canHoService.update(canHoDto, id);
        return ResponseEntity.ok().build();
    }


    // ------------------------------------------------------------------------------------------------------------- //


    @GetMapping("")
    public ResponseEntity<List<CanHoDto>> findAll() {
        List<CanHoDto> list = canHoService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/hoat-dong")
    public ResponseEntity<List<CanHoDto>> findAllByTrangThaiHoatDong() {
        List<CanHoDto> list = canHoService.findAllByTrangThai(true);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/khong-hoat-dong")
    public ResponseEntity<List<CanHoDto>> findAllByTrangThaiKhongHoatDong() {
        List<CanHoDto> list = canHoService.findAllByTrangThai(false);
        return ResponseEntity.ok(list);
    }

    // ------------------------------------------------------------------------------------------------------------- //


    @GetMapping("/{id}/cu-dan")
    public ResponseEntity<?> findAllCuDanByCanHo_Id(@PathVariable("id") Long id) {
        List<CuDanDto> list = cuDanService.findAllByCanHo_Id(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/phuong-tien")
    public ResponseEntity<?> findAllPhuongTienByCanHo_Id(@PathVariable("id") Long id) {
        List<PhuongTienDto> list = phuongTienService.findAllByDaXoaAndCanHo_Id(false, id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/the-cu-dan/chua-kich-hoat")
    public ResponseEntity<?> findAllByCanHo_IdAndDaXoaAndKichHoat(@PathVariable("id") Long id) {
        List<TheCuDanDto> list = theCuDanService.findAllByCanHo_IdAndDaXoaAndKichHoat(id, false, false);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/the-cu-dan")
    public ResponseEntity<?> findAllTheCuDanByCanHo_Id(@PathVariable("id") Long id) {
        List<TheCuDanDto> list = theCuDanService.findAllByCanHo_IdAndDaXoa(id, false);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/hoa-don")
    public ResponseEntity<?> findAllHoaDonDichVuByCanHo_Id(@PathVariable("id") Long id) {
        List<HoaDonDichVuDto> list = hoaDonDichVuService.findAllByCanHo_Id(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/hoa-don-sua-chua")
    public ResponseEntity<?> findAllHoaDonSuaChuaByCanHo_Id(@PathVariable("id") Long id) {
        List<HoaDonSuaChuaDto> list = hoaDonSuaChuaService.findAllByCanHo_Id(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/thong-bao-rieng")
    public ResponseEntity<?> findAllThongByCanHo_Id(@PathVariable("id") Long id) {
        List<ThongBaoRiengDto> list = thongBaoRiengService.findAllByCanHo_Id(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/thong-bao-rieng2")
    public ResponseEntity<?> findAllByCanHo_IdOrderByTrangThaiAscNgayTaoDesc(@PathVariable("id") Long id) {
        List<ThongBaoRiengDto> list = thongBaoRiengService.findAllByCanHo_IdOrderByTrangThaiAscNgayTaoDesc(id);
        return ResponseEntity.ok(list);
    }
}