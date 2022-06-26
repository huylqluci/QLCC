package com.nhom43.quanlychungcubackendgradle.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CuDan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hoTen;

    private Boolean gioiTinh;

    private LocalDate ngaySinh ;

    private String soCCCD;

    private String diaChi;

    private String hinhAnh;

    private String soDienThoai;

    private String email;

    private Boolean chuCanHo;

    private Boolean daXoa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_can_ho")
    private CanHo canHo;
}