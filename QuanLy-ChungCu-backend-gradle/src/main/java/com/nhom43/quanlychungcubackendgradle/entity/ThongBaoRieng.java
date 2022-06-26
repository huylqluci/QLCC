package com.nhom43.quanlychungcubackendgradle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ThongBaoRieng {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tieuDe;

    private String noiDung;

    private LocalDateTime ngayTao;

    private Boolean trangThai;

    private Boolean cuDanGui;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_can_ho")
    private CanHo canHo;
}
