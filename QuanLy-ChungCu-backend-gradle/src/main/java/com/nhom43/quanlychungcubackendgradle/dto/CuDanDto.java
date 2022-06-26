package com.nhom43.quanlychungcubackendgradle.dto;

import com.nhom43.quanlychungcubackendgradle.entity.CanHo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CuDanDto extends AbstractDto<Long> {

    private Long id;
    private String hoTen;
    private Boolean gioiTinh;
    private LocalDate ngaySinh;
    private String soCCCD;
    private String diaChi;
    private String hinhAnh;
    private String soDienThoai;
    private String email;
    private Boolean chuCanHo;
    private Boolean daXoa;
    private CanHo canHo;

}