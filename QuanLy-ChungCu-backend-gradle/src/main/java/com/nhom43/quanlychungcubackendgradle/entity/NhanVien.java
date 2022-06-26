package com.nhom43.quanlychungcubackendgradle.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hoTen;

    private String soDienThoai;

    private String email;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bo_phan", nullable = false)
    private BoPhan boPhan;
}
