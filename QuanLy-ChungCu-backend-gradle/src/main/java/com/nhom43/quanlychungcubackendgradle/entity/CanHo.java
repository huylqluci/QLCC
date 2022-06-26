package com.nhom43.quanlychungcubackendgradle.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CanHo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tenCanHo;

    @Column(nullable = false)
    private String moTa;

    @Column(nullable = false)
    private Integer dienTich;

    @Column(nullable = false)
    private Boolean trangThai;

    private Long idTaiKhoan;
}

