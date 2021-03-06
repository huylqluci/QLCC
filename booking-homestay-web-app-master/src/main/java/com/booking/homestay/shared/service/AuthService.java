package com.booking.homestay.shared.service;


import com.booking.homestay.exception.SpringException;
import com.booking.homestay.model.*;
import com.booking.homestay.repository.IUserRepository;
import com.booking.homestay.repository.IVerificationTokenAccountRepository;
import com.booking.homestay.repository.IVerificationTokenPasswordRepository;
import com.booking.homestay.security.service.JwtProvider;
import com.booking.homestay.shared.dto.*;
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
    private final IUserRepository IUserRepository;
    private final IVerificationTokenAccountRepository IVerificationTokenAccountRepository;
    private final IVerificationTokenPasswordRepository IVerificationTokenPasswordRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;


    public void register(RegisterRequest registerRequest) {
        Optional<User> username = IUserRepository.findByUserName(registerRequest.getUserName());
        Optional<User> email = IUserRepository.findByEmail(registerRequest.getEmail());
        Optional<User> phone = IUserRepository.findByPhone(registerRequest.getPhone());
        if (username.isPresent() || email.isPresent() || phone.isPresent()) {
            throw new SpringException("T??i kho???n, email ho???c s??? ??i???n tho???i b??? tr??ng");
        }
        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setEmail(registerRequest.getEmail());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPhone(registerRequest.getPhone());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setEnabled(false);
        user.setStatus(true);
        user.setRole("Member");
        IUserRepository.save(user);
        String token = generateVerificationTokenAccount(user);
        mailService.sendMail(new NotificationEmail("Vui l??ng k??ch ho???t t??i kho???n m???i c???a b???n",
                user.getEmail(), "C???m ??n b???n ???? ????ng k?? t???i kho???n t???i website ch??ng t??i, " +
                "Vui l??ng nh???p v??o ???????ng d???n n??y ????? k??ch ho???t t??i kho???n b???n m???i ????ng k??, " +
                "H???n s??? d???ng trong v??ng 2 gi??? : " +
                "http://localhost:4200/accountVerification/" + token));
    }

    private String generateVerificationTokenAccount(User user) {
        String token = UUID.randomUUID().toString();
        VerificationTokenAccount verificationTokenAccount = new VerificationTokenAccount();
        verificationTokenAccount.setToken(token);
        verificationTokenAccount.setUser(user);
        verificationTokenAccount.setExpiryDate(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()));
        IVerificationTokenAccountRepository.save(verificationTokenAccount);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationTokenAccount> verificationToken = IVerificationTokenAccountRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringException("M?? token kh??ng h???p l???")));
    }

    private void fetchUserAndEnable(VerificationTokenAccount verificationTokenAccount) {
        Instant time = Instant.now();
        if (time.isAfter(verificationTokenAccount.getExpiryDate())) {
            IVerificationTokenAccountRepository.deleteById(verificationTokenAccount.getId());
            IUserRepository.deleteById(verificationTokenAccount.getUser().getId());
            throw new SpringException("M?? token ???? h???t h???n.");
        }
        String username = verificationTokenAccount.getUser().getUserName();
        User user = IUserRepository.findByUserName(username).orElseThrow(() -> new SpringException("Kh??ng t??m th???y t??n t??i kho???n l??: " + username));
        user.setEnabled(true);
        IUserRepository.save(user);
        IVerificationTokenAccountRepository.deleteById(verificationTokenAccount.getId());
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        User user = IUserRepository.findByUserName(loginRequest.getUserName()).orElseThrow(() -> new SpringException("T??i kho???n ho???c m???t kh???u kh??ng ????ng: " + loginRequest.getUserName()));
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshToken.getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .userName(loginRequest.getUserName())
                .role(user.getRole())
                .image(user.getImage())
                .build();
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        try{
            org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                    getContext().getAuthentication().getPrincipal();
            return IUserRepository.findByUserName(principal.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Kh??ng t??m th???y t??i kho???n n??o c?? user name l?? - " + principal.getUsername()));
        }catch (Exception e){
          return null;
        }
    }

    public void forgotPassword(EmailRequest emailRequest) {
        User user = IUserRepository.findByEmail(emailRequest.getEmail()).orElseThrow(() -> new SpringException("Kh??ng t??m th???y t??i kho???n n??o c?? email l??: " + emailRequest.getEmail()));
        String token = generateVerificationTokenPassword(user);
        mailService.sendMail(new NotificationEmail("X??c nh???n y??u c???u l???y l???i m???t kh???u",
                user.getEmail(), "Vui l??ng nh???p v??o ???????ng d???n b??n d?????i ????? l??m m???i m???t kh???u c???a b???n, " +
                "H???n s??? d???ng 2 gi???: " +
                "http://localhost:4200/passwordVerification/" + token));
    }

    private String generateVerificationTokenPassword(User user) {
        String token = UUID.randomUUID().toString();
        VerificationTokenPassword verificationTokenPassword = new VerificationTokenPassword();
        verificationTokenPassword.setToken(token);
        verificationTokenPassword.setUser(user);
        verificationTokenPassword.setExpiryDate(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()));
        IVerificationTokenPasswordRepository.save(verificationTokenPassword);
        return token;
    }

    public String verifyPassword(String token) {
        VerificationTokenPassword verificationToken = IVerificationTokenPasswordRepository.findByToken(token)
                .orElseThrow(() -> new SpringException("M?? token kh??ng h???p l??? - " + token));
        Instant time = Instant.now();
        if (time.isAfter(verificationToken.getExpiryDate())) {
            IVerificationTokenAccountRepository.deleteById(verificationToken.getId());
            throw new SpringException("M?? token ???? h???t h???n");
        }
        String username = verificationToken.getUser().getUserName();
        return username;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken(), refreshTokenRequest.getUserName());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUserName());
        User user = IUserRepository.findByUserName(refreshTokenRequest.getUserName()).orElseThrow(() -> new SpringException("Kh??ng t??m th???y t??n t??i kho???n l??: " + refreshTokenRequest.getUserName()));
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .userName(refreshTokenRequest.getUserName())
                .role(user.getRole())
                .build();
    }


    public void editPassword(AccoutEditRequest accoutEditRequest) {
        User username = IUserRepository.findByUserName(accoutEditRequest.getUserName()).orElseThrow(() -> new SpringException("Kh??ng t??m th???y t??n t??i kho???n l??: " + accoutEditRequest.getUserName()));
        username.setPassword(passwordEncoder.encode(accoutEditRequest.getPassword()));
        IUserRepository.save(username);
        List<VerificationTokenPassword> listTokenByUser = IVerificationTokenPasswordRepository.findByUser_Id(username.getId());
        for (VerificationTokenPassword verificationTokenPassword : listTokenByUser) {
            IVerificationTokenPasswordRepository.deleteById(verificationTokenPassword.getId());
        }
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }


}
