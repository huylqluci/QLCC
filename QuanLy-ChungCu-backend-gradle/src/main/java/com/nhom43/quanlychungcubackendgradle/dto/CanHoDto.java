package com.nhom43.quanlychungcubackendgradle.dto;

import com.nhom43.quanlychungcubackendgradle.entity.CuDan;
import com.nhom43.quanlychungcubackendgradle.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CanHoDto extends AbstractDto<Long> {

    private Long id;
    private String tenCanHo;
    private String moTa;
    private Integer dienTich;
    private Boolean trangThai;
    private Long idTaiKhoan;
    private String emailTaiKhoan;
    private Long soLuongCuDan;
    private CuDan chuCanHo;
    private User taiKhoan;

}