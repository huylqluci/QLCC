
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
public class HoaDonSuaChua {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime ngayTao;

    private Boolean trangThai;

    private String tenNguoiThanhToan;
    private String soDienThoai;
    private String loaiHinhThanhToan;
    private LocalDateTime ngayThanhToan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_can_ho", nullable = false)
    private CanHo canHo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_nhan_vien", nullable = false)
    private NhanVien nhanVien;

}