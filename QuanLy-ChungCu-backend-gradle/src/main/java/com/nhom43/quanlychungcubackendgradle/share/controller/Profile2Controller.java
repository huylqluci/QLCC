package com.nhom43.quanlychungcubackendgradle.share.controller;


import com.nhom43.quanlychungcubackendgradle.share.dto.request.ProfileRequest;
import com.nhom43.quanlychungcubackendgradle.share.dto.response.ProfileResponse;
import com.nhom43.quanlychungcubackendgradle.share.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RequestMapping("/api/profile")
@RestController
@AllArgsConstructor
public class Profile2Controller {

    private final ProfileService profileService;

    @GetMapping("/check")
    public ResponseEntity<String> getAllProfile() {
        return status(HttpStatus.OK).body(profileService.checkUser());
    }


    @PutMapping("/update")
    public ResponseEntity<Void> updateProfile(@RequestBody ProfileRequest profileRequest) {
        profileService.update(profileRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> editProfile(@RequestBody ProfileRequest profileRequest) {
        profileService.edit(profileRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/edit/password")
    public ResponseEntity<Void> editPassword(@RequestBody ProfileRequest profileRequest) {
        profileService.editPassword(profileRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {
        return status(HttpStatus.OK).body(profileService.getProfile());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfileId(@PathVariable Long id) {
        return status(HttpStatus.OK).body(profileService.getProfileId(id));
    }

}
