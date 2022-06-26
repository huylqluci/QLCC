package com.nhom43.quanlychungcubackendgradle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ThongBaoDto extends AbstractDto<Long> {

    private Long id;
    private String tieuDe;
    private String noiDung;
    private LocalDateTime ngayTao;

}