package com.nhom43.quanlychungcubackendgradle.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChiTietHoaDonDichVu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double donGia;

    private Integer soLuong;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dich_vu_co_dinh", nullable = false)
    private DichVuCoDinh dichVuCoDinh;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "id_hoa_don_dich_vu", nullable = false)
    private HoaDonDichVu hoaDonDichVu;

}

