package com.nhom43.quanlychungcubackendgradle.share.pay.service;

import com.nhom43.quanlychungcubackendgradle.dto.HoaDonDichVuDto;
import com.nhom43.quanlychungcubackendgradle.dto.HoaDonSuaChuaDto;
import com.nhom43.quanlychungcubackendgradle.entity.*;
import com.nhom43.quanlychungcubackendgradle.ex.SpringException;
import com.nhom43.quanlychungcubackendgradle.mapper.HoaDonDichVuMapper;
import com.nhom43.quanlychungcubackendgradle.mapper.HoaDonSuaChuaMapper;
import com.nhom43.quanlychungcubackendgradle.repository.*;
import com.nhom43.quanlychungcubackendgradle.share.model.NotificationEmail;
import com.nhom43.quanlychungcubackendgradle.share.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class PaypalService {

    private final CuDanRepository cuDanRepository;
    private final HoaDonDichVuRepository hoaDonDichVuRepository;
    private final ChiTietHoaDonDichVuRepository chiTietHoaDonDichVuRepository;
    private final ChiTietHoaDonSuaChuaRepository chiTietHoaDonSuaChuaRepository;
    private final HoaDonSuaChuaRepository hoaDonSuaChuaRepository;
    private final HoaDonDichVuMapper hoaDonDichVuMapper;
    private final HoaDonSuaChuaMapper hoaDonSuaChuaMapper;
    private final UserRepository userRepository;
    private final MailService mailService;

    @Transactional(readOnly = true)
    public HoaDonDichVuDto kiemTraTrangThaiThanhToan_HDDV(Long id) {
//        LocalDateTime time = LocalDateTime.now();
        HoaDonDichVu hoaDonDichVu = hoaDonDichVuRepository.findById(id).orElseThrow(() ->
                new SpringException("Không tồn hóa đơn DVCD với mã ID:" + id));
        if (hoaDonDichVu.getTrangThai() == true) {
            throw new SpringException("Hóa đơn này của bạn đã được thanh toán");
        }
        List<ChiTietHoaDonDichVu> list = chiTietHoaDonDichVuRepository.findByHoaDonDichVu_Id(hoaDonDichVu.getId());
        double tongTien = 0;
        for (ChiTietHoaDonDichVu chiTietHoaDonDichVu : list) {
            tongTien += chiTietHoaDonDichVu.getDonGia() * chiTietHoaDonDichVu.getSoLuong();
        }
        HoaDonDichVuDto kq = hoaDonDichVuMapper.toDto(hoaDonDichVu);
        kq.setSoTien(tongTien);
        return kq;
    }

    public HoaDonSuaChuaDto kiemTraTrangThaiThanhToan_HDSC(Long id) {
        HoaDonSuaChua hoaDonSuaChua = hoaDonSuaChuaRepository.findById(id).orElseThrow(() ->
                new SpringException("Không tồn tại hóa đơn DVSC với mã ID:" + id));
        if (hoaDonSuaChua.getTrangThai() == true) {
            throw new SpringException("Hóa đơn này của bạn đã được thanh toán");
        }
        List<ChiTietHoaDonSuaChua> list = chiTietHoaDonSuaChuaRepository.findByHoaDonSuaChua_Id(hoaDonSuaChua.getId());
        double tongTien = 0;
        for (ChiTietHoaDonSuaChua chiTietHoaDonSuaChua : list) {
            tongTien += chiTietHoaDonSuaChua.getDonGia() * chiTietHoaDonSuaChua.getSoLuong();
        }
        HoaDonSuaChuaDto kq = hoaDonSuaChuaMapper.toDto(hoaDonSuaChua);
        kq.setSoTien(tongTien);
        return kq;
    }

    public void submitThanhToan_HDDV(Long id) {
        HoaDonDichVu hoaDonDichVu = hoaDonDichVuRepository.findById(id).orElseThrow(() ->
                new SpringException("Không tồn hóa đơn DVCD với mã ID:" + id));
        LocalDateTime time = LocalDateTime.now();
        CanHo canHo = hoaDonDichVu.getCanHo();
        CuDan cuDan = cuDanRepository.findByCanHoAndChuCanHo(canHo, true);

        hoaDonDichVu.setTrangThai(true);
        hoaDonDichVu.setNgayThanhToan(time);
        hoaDonDichVu.setLoaiHinhThanhToan("Paypal");
        hoaDonDichVu.setTenNguoiThanhToan(cuDan.getHoTen());
        hoaDonDichVu.setSoDienThoai(cuDan.getSoDienThoai());

        User user = userRepository.getById(canHo.getIdTaiKhoan());
        hoaDonDichVuRepository.save(hoaDonDichVu);
        mailService.sendMail(new NotificationEmail("Thanh toán thành công",
                user.getEmail(), "Căn hộ: " + canHo.getTenCanHo() + "\n"
                + "Thanh toán thành công hóa đơn dịch vụ cố định, id:" + hoaDonDichVu.getId() + "\n"
                + "Thời gian thanh toán: " + time));
    }

    public void submitThanhToan_HDSC(Long id) {
        HoaDonSuaChua hoaDonSuaChua = hoaDonSuaChuaRepository.findById(id).orElseThrow(() ->
                new SpringException("Không tồn tại hóa đơn DVSC với mã ID:" + id));
        LocalDateTime time = LocalDateTime.now();
        CanHo canHo = hoaDonSuaChua.getCanHo();
        CuDan cuDan = cuDanRepository.findByCanHoAndChuCanHo(canHo, true);

        hoaDonSuaChua.setTrangThai(true);
        hoaDonSuaChua.setNgayThanhToan(time);
        hoaDonSuaChua.setLoaiHinhThanhToan("Paypal");
        hoaDonSuaChua.setTenNguoiThanhToan(cuDan.getHoTen());
        hoaDonSuaChua.setSoDienThoai(cuDan.getSoDienThoai());

        User user = userRepository.getById(canHo.getIdTaiKhoan());
        hoaDonSuaChuaRepository.save(hoaDonSuaChua);
        mailService.sendMail(new NotificationEmail("Thanh toán thành công",
                user.getEmail(), "Căn hộ: " + canHo.getTenCanHo() + "\n"
                + "Thanh toán thành công hóa đơn dịch vụ sửa chữa, id:" + hoaDonSuaChua.getId() + "\n"
                + "Thời gian thanh toán: " + time));
    }
}
