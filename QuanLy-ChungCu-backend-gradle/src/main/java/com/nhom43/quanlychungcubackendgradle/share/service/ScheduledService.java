package com.nhom43.quanlychungcubackendgradle.share.service;

import com.nhom43.quanlychungcubackendgradle.entity.*;
import com.nhom43.quanlychungcubackendgradle.repository.*;
import com.nhom43.quanlychungcubackendgradle.share.model.NotificationEmail;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ScheduledService {

    private final VerificationTokenAccountRepository verificationTokenAccountRepository;
    private final VerificationTokenPasswordRepository verificationTokenPasswordRepository;
    private final CanHoRepository canHoRepository;
    private final CuDanRepository cuDanRepository;
    private final UserRepository userRepository;
    private final HoaDonDichVuRepository hoaDonDichVuRepository;
    private final DichVuCoDinhRepository dichVuCoDinhRepository;
    private final ChiTietHoaDonDichVuRepository chiTietHoaDonDichVuRepository;
    private final PhuongTienRepository phuongTienRepository;
    private final MailService mailService;


    @Scheduled(fixedDelay = 6 * 60 * 60 * 1000)  // 6 tiếng lọc một lần
    public void TokenCleaning() {
        try {
            Instant time = Instant.now();
            System.out.println("Lọc token password " + time);
            List<VerificationTokenPassword> tokenPasswordList = verificationTokenPasswordRepository.findAll();
            if (tokenPasswordList.size() != 0) {
                for (VerificationTokenPassword verificationTokenPassword : tokenPasswordList) {
                    if (time.isAfter(verificationTokenPassword.getExpiryDate())) {
                        verificationTokenPasswordRepository.deleteById(verificationTokenPassword.getId());
                    }
                }
            }

            List<VerificationTokenAccount> tokenAccountList = verificationTokenAccountRepository.findAll();
            if (tokenAccountList.size() != 0) {
                for (VerificationTokenAccount verificationTokenAccount : tokenAccountList) {
                    if (time.isAfter(verificationTokenAccount.getExpiryDate())) {
                        verificationTokenAccountRepository.deleteById(verificationTokenAccount.getId());
                        CanHo canHo = canHoRepository.getCanHoByIdTaiKhoan(verificationTokenAccount.getUser().getId());
                        canHo.setIdTaiKhoan(null);
                        userRepository.deleteById(verificationTokenAccount.getUser().getId());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Lọc token bị lỗi " + e);
        }
    }

    @Scheduled(cron = "0 30 8 L * ?") // 1 tháng 1 lần vào ngày cuối cuàng của tháng lúc 8h 00
    // Test time hiện tại @Scheduled(cron = "0 m h * * ?")
//    @Scheduled(cron = "0 29 03 * * ?")
    public void TaoHoaDonHangThang() {
        try {
            LocalDateTime time = LocalDateTime.now();
            System.out.println("-- Tạo hóa đơn cố định " + time);
            // Lấy ra danh sách các căn hộ đã có ng sinh sống
            List<CanHo> canHoList = canHoRepository.findAllByTrangThai(true);
            for (CanHo canHo : canHoList) {
                double tongTien = 0;
                // Tạo hóa đơn
                HoaDonDichVu hoaDonDichVu = new HoaDonDichVu(null, time, false, null, null, null, null, canHo);
                hoaDonDichVuRepository.save(hoaDonDichVu);
                // Thêm hóa đơn chi tiết phí dịch vụ bảo trì
                DichVuCoDinh dvBaoTri = dichVuCoDinhRepository.findFirstByTen("Bao tri");
                ChiTietHoaDonDichVu ctBaoTri =
                        new ChiTietHoaDonDichVu(null, dvBaoTri.getDonGia(), canHo.getDienTich(), dvBaoTri, hoaDonDichVu);
                chiTietHoaDonDichVuRepository.save(ctBaoTri);
                tongTien += ctBaoTri.getDonGia() * ctBaoTri.getSoLuong();
                // Thêm hóa đơn chi tiết gửi xe
                int soLuongXeOto = phuongTienRepository.countPhuongTienByCanHoAndLoaiXeAndDaXoa(canHo, "O to", false);
                if (soLuongXeOto > 0) {
                    DichVuCoDinh dvGuiXeOto = dichVuCoDinhRepository.findFirstByTen("Gui xe Oto");
                    ChiTietHoaDonDichVu ctGuiXeOto =
                            new ChiTietHoaDonDichVu(null, dvGuiXeOto.getDonGia(), soLuongXeOto, dvGuiXeOto, hoaDonDichVu);
                    chiTietHoaDonDichVuRepository.save(ctGuiXeOto);
                    tongTien += ctGuiXeOto.getDonGia() * ctGuiXeOto.getSoLuong();
                }
                int soLuongXeMay = phuongTienRepository.countPhuongTienByCanHoAndLoaiXeAndDaXoa(canHo, "May", false);
                if (soLuongXeMay > 0) {
                    DichVuCoDinh dvGuiXeMay = dichVuCoDinhRepository.findFirstByTen("Gui xe May");
                    ChiTietHoaDonDichVu ctGuiXeMay =
                            new ChiTietHoaDonDichVu(null, dvGuiXeMay.getDonGia(), soLuongXeMay, dvGuiXeMay, hoaDonDichVu);
                    chiTietHoaDonDichVuRepository.save(ctGuiXeMay);
                    tongTien += ctGuiXeMay.getDonGia() * ctGuiXeMay.getSoLuong();
                }
                int soLuongXeMayDien = phuongTienRepository.countPhuongTienByCanHoAndLoaiXeAndDaXoa(canHo, "May dien", false);
                if (soLuongXeMayDien > 0) {
                    DichVuCoDinh dvGuiXeMayDien = dichVuCoDinhRepository.findFirstByTen("Gui xe May dien");
                    ChiTietHoaDonDichVu ctGuiXeMayDien =
                            new ChiTietHoaDonDichVu(null, dvGuiXeMayDien.getDonGia(), soLuongXeMayDien, dvGuiXeMayDien, hoaDonDichVu);
                    chiTietHoaDonDichVuRepository.save(ctGuiXeMayDien);
                    tongTien += ctGuiXeMayDien.getDonGia() * ctGuiXeMayDien.getSoLuong();
                }
                int soLuongXeDap = phuongTienRepository.countPhuongTienByCanHoAndLoaiXeAndDaXoa(canHo, "Dap", false);
                if (soLuongXeDap > 0) {
                    DichVuCoDinh dvGuiXeDap = dichVuCoDinhRepository.findFirstByTen("Gui xe Dap");
                    ChiTietHoaDonDichVu ctGuiXeDap =
                            new ChiTietHoaDonDichVu(null, dvGuiXeDap.getDonGia(), soLuongXeDap, dvGuiXeDap, hoaDonDichVu);
                    chiTietHoaDonDichVuRepository.save(ctGuiXeDap);
                    tongTien += ctGuiXeDap.getDonGia() * ctGuiXeDap.getSoLuong();
                }

                // Gửi thông tin tổng tiền hóa đơn đến mail của chủ căn hộ và mail gắn liền vs tài khoản gắn vs chủ căn hộ

                CuDan chuCanHo = cuDanRepository.findByCanHoAndChuCanHo(canHo, true);
                if (chuCanHo.getEmail() != null)
                    guiMailThongBaoToi(time, canHo, tongTien, chuCanHo.getEmail());
                User user1 = null;
                if (canHo.getIdTaiKhoan() != null) {
                    user1 = userRepository.findById(canHo.getIdTaiKhoan()).get();
                    if (user1.getEmail() != null)
                        guiMailThongBaoToi(time, canHo, tongTien, user1.getEmail());

                }
            }
        } catch (Exception e) {
            System.out.println("Tạo hóa đơn cố định bị lỗi: " + e);
            throw e;
        }
    }

    private void guiMailThongBaoToi(LocalDateTime time, CanHo canHo, double tongTien, String email) {
        mailService.sendMail(new NotificationEmail("Thông báo thu tiền tháng " + time.getMonthValue()
                + ", năm " + time.getYear(),
                email,
                "Chung cư ABC xin thông báo tới quý khách, hóa đơn cố định tháng này đã có " +
                        time.getMonthValue() + "/" + time.getYear()
//                        "08/2021"
                        +
                        "\nVui lòng đăng nhập để xem chi tiết thông tin hóa đơn!  : "
                        + "\nTổng tiền tháng này phải đóng của phòng " + canHo.getTenCanHo() + " là: "
                        + tongTien + "\nCảm ơn bạn đã đọc mail!"));
    }


}
