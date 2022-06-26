package com.nhom43.quanlychungcubackendgradle.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LoaiSuaChua {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ten;

    private Double donGia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bo_phan")
    private BoPhan boPhan;

}
