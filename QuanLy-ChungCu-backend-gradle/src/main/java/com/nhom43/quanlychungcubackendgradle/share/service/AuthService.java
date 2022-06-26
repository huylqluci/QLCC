package com.nhom43.quanlychungcubackendgradle.share.service;


import com.nhom43.quanlychungcubackendgradle.entity.*;
import com.nhom43.quanlychungcubackendgradle.ex.SpringException;
import com.nhom43.quanlychungcubackendgradle.repository.CanHoRepository;
import com.nhom43.quanlychungcubackendgradle.repository.UserRepository;
import com.nhom43.quanlychungcubackendgradle.repository.VerificationTokenAccountRepository;
import com.nhom43.quanlychungcubackendgradle.repository.VerificationTokenPasswordRepository;
import com.nhom43.quanlychungcubackendgradle.share.config.AppConfig;
import com.nhom43.quanlychungcubackendgradle.share.dto.request.*;
import com.nhom43.quanlychungcubackendgradle.share.dto.response.AuthenticationResponse;
import com.nhom43.quanlychungcubackendgradle.share.model.NotificationEmail;
import com.nhom43.quanlychungcubackendgradle.share.security.service.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CanHoRepository canHoRepository;
    private final VerificationTokenAccountRepository verificationTokenAccountRepository;
    private final VerificationTokenPasswordRepository verificationTokenPasswordRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final AppConfig appConfig;

    public void register(RegisterRequest registerRequest) {
        registerRequest.setUsername(registerRequest.getUsername().toLowerCase());
        Optional<User> username = userRepository.findByUsername(registerRequest.getUsername());
        Optional<User> email = userRepository.findByEmail(registerRequest.getEmail());
        if (username.isPresent() || email.isPresent()) {
            throw new SpringException("Tài khoản, email bị trùng");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        user.setStatus(true);
        user.setImage(registerRequest.getImage());
        user.setRole(registerRequest.getRole());
        userRepository.save(user);
        String token = generateVerificationTokenAccount(user);
        mailService.sendMail(new NotificationEmail("Vui lòng kích hoạt tài khoản mới của bạn",
                user.getEmail(),
                "Vui lòng nhấp vào đường dẫn này để kích hoạt tài khoản bạn mới đăng ký, " +
                "Hạn sử dụng trong vòng 6 giờ : "
                + appConfig.getAppUrl() + "/accountVerification/"
                + token));
    }

    public void register2(RegisterRequest2 registerRequest) {
        registerRequest.setUsername(registerRequest.getUsername().toLowerCase());
        Optional<User> username = userRepository.findByUsername(registerRequest.getUsername());
        Optional<User> email = userRepository.findByEmail(registerRequest.getEmail());
        if (username.isPresent() || email.isPresent()) {
            throw new SpringException("Tài khoản, email bị trùng");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        user.setStatus(true);
        user.setImage(registerRequest.getImage());
        user.setRole(registerRequest.getRole());
        User user1 = userRepository.save(user);
        CanHo canHo = canHoRepository.getById(registerRequest.getIdCanHo());
        canHo.setIdTaiKhoan(user1.getId());
        String token = generateVerificationTokenAccount(user);
        mailService.sendMail(new NotificationEmail("Vui lòng kích hoạt tài khoản mới của bạn",
                user.getEmail(),
                "Vui lòng nhấp vào đường dẫn này để kích hoạt tài khoản bạn mới đăng ký, " +
                        "Hạn sử dụng trong vòng 6 giờ : "
                        + appConfig.getAppUrl() + "/accountVerification/"
                        + token));
    }

    private String generateVerificationTokenAccount(User user) {
        String token = UUID.randomUUID().toString();
        VerificationTokenAccount verificationTokenAccount = new VerificationTokenAccount();
        verificationTokenAccount.setToken(token);
        verificationTokenAccount.setUser(user);
        verificationTokenAccount.setExpiryDate(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()));
        verificationTokenAccountRepository.save(verificationTokenAccount);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationTokenAccount> verificationToken = verificationTokenAccountRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringException("Mã token không hợp lệ")));
    }

    private void fetchUserAndEnable(VerificationTokenAccount verificationTokenAccount) {
        Instant time = Instant.now();
        if (time.isAfter(verificationTokenAccount.getExpiryDate())) {
            verificationTokenAccountRepository.deleteById(verificationTokenAccount.getId());
            userRepository.deleteById(verificationTokenAccount.getUser().getId());
            throw new SpringException("Mã token đã hết hạn.");
        }
        String username = verificationTokenAccount.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringException("Không tìm thấy tên tài khoản là: " + username));
        user.setEnabled(true);
        userRepository.save(user);
        verificationTokenAccountRepository.deleteById(verificationTokenAccount.getId());
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
//        loginRequest.setUsername(loginRequest.getUsername().toLowerCase());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateJwtToken(authenticate);
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new SpringException("Tài khoản hoặc mật khẩu không đúng: " + loginRequest.getUsername()));
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(user);
        Long idCanHo = null;
        if (user.getRole().equals("User")) idCanHo = canHoRepository.getCanHoByIdTaiKhoan(user.getId()).getId();
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshToken.getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .role(user.getRole())
                .image(user.getImage())
                .idCanHo(idCanHo)
                .email(user.getEmail())
                .id(user.getId())
                .build();
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        try {
            org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                    getContext().getAuthentication().getPrincipal();
            return userRepository.findByUsername(principal.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản nào có user name là - " + principal.getUsername()));
        } catch (Exception e) {
            return null;
        }
    }

    public void forgotPassword(EmailRequest emailRequest) {
        User user = userRepository.findByEmail(emailRequest.getEmail()).orElseThrow(() ->
                new SpringException("Không tìm thấy tài khoản nào có email là: " + emailRequest.getEmail()));
        String token = generateVerificationTokenPassword(user);
        mailService.sendMail(new NotificationEmail("Xác nhận yêu cầu lấy lại mật khẩu",
                user.getEmail(), "Vui lòng nhấp vào đường dẫn bên dưới để làm mới mật khẩu của bạn, "
                + "Hạn sủ dụng 6 giờ: " + "\n"
                + appConfig.getAppUrl() + "/passwordVerification/" + token));
    }

    private String generateVerificationTokenPassword(User user) {
        String token = UUID.randomUUID().toString();
        VerificationTokenPassword verificationTokenPassword = new VerificationTokenPassword();
        verificationTokenPassword.setToken(token);
        verificationTokenPassword.setUser(user);
        verificationTokenPassword.setExpiryDate(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()));
        verificationTokenPasswordRepository.save(verificationTokenPassword);
        return token;
    }

    public String verifyPassword(String token) {
        VerificationTokenPassword verificationToken = verificationTokenPasswordRepository.findByToken(token)
                .orElseThrow(() -> new SpringException("Mã token không hợp lệ - " + token));
        Instant time = Instant.now();
        if (time.isAfter(verificationToken.getExpiryDate())) {
            verificationTokenAccountRepository.deleteById(verificationToken.getId());
            throw new SpringException("Mã token đã hết hạn");
        }
        String username = verificationToken.getUser().getUsername();
        return username;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken(), refreshTokenRequest.getUsername());
        String token = jwtProvider.generateJwtTokenWithUsername(refreshTokenRequest.getUsername());
        User user = userRepository.findByUsername(refreshTokenRequest.getUsername()).orElseThrow(() -> new SpringException("Không tìm thấy tên tài khoản là: " + refreshTokenRequest.getUsername()));
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .role(user.getRole())
                .build();
    }


    public void editPassword(AccoutEditRequest accoutEditRequest) {
        User username = userRepository.findByUsername(accoutEditRequest.getUsername()).orElseThrow(() ->
                new SpringException("Không tìm thấy tên tài khoản là: " + accoutEditRequest.getUsername()));
        username.setPassword(passwordEncoder.encode(accoutEditRequest.getPassword()));
        userRepository.save(username);
        List<VerificationTokenPassword> listTokenByUser =
                verificationTokenPasswordRepository.findByUser_Id(username.getId());
        for (VerificationTokenPassword verificationTokenPassword : listTokenByUser) {
            verificationTokenPasswordRepository.deleteById(verificationTokenPassword.getId());
        }
    }

    public void editStatus (AccoutEditRequest accoutEditRequest) {
        User username = userRepository.findByUsername(accoutEditRequest.getUsername()).orElseThrow(() ->
                new SpringException("Không tìm thấy tên tài khoản là: " + accoutEditRequest.getUsername()));
        username.setStatus(accoutEditRequest.getStatus());
        userRepository.save(username);
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }


}
