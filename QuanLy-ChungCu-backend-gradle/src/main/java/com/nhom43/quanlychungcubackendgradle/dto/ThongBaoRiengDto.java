package com.nhom43.quanlychungcubackendgradle.dto;

import com.nhom43.quanlychungcubackendgradle.entity.CanHo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ThongBaoRiengDto extends AbstractDto<Long> {

    private Long id;
    private String tieuDe;
    private String noiDung;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime ngayTao;
    private Boolean trangThai;
    private Boolean cuDanGui;
    private CanHo canHo;

}