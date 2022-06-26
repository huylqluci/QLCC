package com.nhom43.quanlychungcubackendgradle.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PhuongTien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loaiXe;

    private String tenXe;

    private boolean daXoa;

    private String bienKiemSoat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_can_ho", nullable = false)
    private CanHo canHo;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_the_cu_dan", nullable = false)
    private TheCuDan theCuDan;
}