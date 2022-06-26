package com.nhom43.quanlychungcubackendgradle.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChiTietHoaDonSuaChua {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double donGia;

    private Integer soLuong;

    private String moTa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_loai_sua_chua", nullable = false)
    private LoaiSuaChua loaiSuaChua;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "id_hoa_don_sua_chua", nullable = false)
    private HoaDonSuaChua hoaDonSuaChua;
}
