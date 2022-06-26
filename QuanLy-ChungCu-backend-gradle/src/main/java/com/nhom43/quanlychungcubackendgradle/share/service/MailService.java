package com.nhom43.quanlychungcubackendgradle.share.service;


import com.nhom43.quanlychungcubackendgradle.share.model.NotificationEmail;
import com.nhom43.quanlychungcubackendgradle.ex.SpringException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("support@chungcuABC.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Đã gửi email!");
        } catch (MailException e) {
            throw new SpringException("Đã xảy ra ngoại lệ khi gửi thư đến " + notificationEmail.getRecipient()
                    + ". Nếu bạn dùng gmail. Hãy kiểm tra Cài đặt bảo mật tài khoản và bật cho phép ứng dụng không tin tưởng!");
        }
    }

}
