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
            throw new SpringException("T??i kho???n, email b??? tr??ng");
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
        mailService.sendMail(new NotificationEmail("Vui l??ng k??ch ho???t t??i kho???n m???i c???a b???n",
                user.getEmail(),
                "Vui l??ng nh???p v??o ???????ng d???n n??y ????? k??ch ho???t t??i kho???n b???n m???i ????ng k??, " +
                "H???n s??? d???ng trong v??ng 6 gi??? : "
                + appConfig.getAppUrl() + "/accountVerification/"
                + token));
    }

    public void register2(RegisterRequest2 registerRequest) {
        registerRequest.setUsername(registerRequest.getUsername().toLowerCase());
        Optional<User> username = userRepository.findByUsername(registerRequest.getUsername());
        Optional<User> email = userRepository.findByEmail(registerRequest.getEmail());
        if (username.isPresent() || email.isPresent()) {
            throw new SpringException("T??i kho???n, email b??? tr??ng");
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
        mailService.sendMail(new NotificationEmail("Vui l??ng k??ch ho???t t??i kho???n m???i c???a b???n",
                user.getEmail(),
                "Vui l??ng nh???p v??o ???????ng d???n n??y ????? k??ch ho???t t??i kho???n b???n m???i ????ng k??, " +
                        "H???n s??? d???ng trong v??ng 6 gi??? : "
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
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringException("M?? token kh??ng h???p l???")));
    }

    private void fetchUserAndEnable(VerificationTokenAccount verificationTokenAccount) {
        Instant time = Instant.now();
        if (time.isAfter(verificationTokenAccount.getExpiryDate())) {
            verificationTokenAccountRepository.deleteById(verificationTokenAccount.getId());
            userRepository.deleteById(verificationTokenAccount.getUser().getId());
            throw new SpringException("M?? token ???? h???t h???n.");
        }
        String username = verificationTokenAccount.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringException("Kh??ng t??m th???y t??n t??i kho???n l??: " + username));
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
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new SpringException("T??i kho???n ho???c m???t kh???u kh??ng ????ng: " + loginRequest.getUsername()));
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
                    .orElseThrow(() -> new UsernameNotFoundException("Kh??ng t??m th???y t??i kho???n n??o c?? user name l?? - " + principal.getUsername()));
        } catch (Exception e) {
            return null;
        }
    }

    public void forgotPassword(EmailRequest emailRequest) {
        User user = userRepository.findByEmail(emailRequest.getEmail()).orElseThrow(() ->
                new SpringException("Kh??ng t??m th???y t??i kho???n n??o c?? email l??: " + emailRequest.getEmail()));
        String token = generateVerificationTokenPassword(user);
        mailService.sendMail(new NotificationEmail("X??c nh???n y??u c???u l???y l???i m???t kh???u",
                user.getEmail(), "Vui l??ng nh???p v??o ???????ng d???n b??n d?????i ????? l??m m???i m???t kh???u c???a b???n, "
                + "H???n s??? d???ng 6 gi???: " + "\n"
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
                .orElseThrow(() -> new SpringException("M?? token kh??ng h???p l??? - " + token));
        Instant time = Instant.now();
        if (time.isAfter(verificationToken.getExpiryDate())) {
            verificationTokenAccountRepository.deleteById(verificationToken.getId());
            throw new SpringException("M?? token ???? h???t h???n");
        }
        String username = verificationToken.getUser().getUsername();
        return username;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken(), refreshTokenRequest.getUsername());
        String token = jwtProvider.generateJwtTokenWithUsername(refreshTokenRequest.getUsername());
        User user = userRepository.findByUsername(refreshTokenRequest.getUsername()).orElseThrow(() -> new SpringException("Kh??ng t??m th???y t??n t??i kho???n l??: " + refreshTokenRequest.getUsername()));
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
                new SpringException("Kh??ng t??m th???y t??n t??i kho???n l??: " + accoutEditRequest.getUsername()));
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
                new SpringException("Kh??ng t??m th???y t??n t??i kho???n l??: " + accoutEditRequest.getUsername()));
        username.setStatus(accoutEditRequest.getStatus());
        userRepository.save(username);
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }


}
