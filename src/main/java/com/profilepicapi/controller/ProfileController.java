package com.profilepicapi.controller;

import com.profilepicapi.model.Profile;
import com.profilepicapi.service.UserProfileService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

// UserProfileController.java
@RestController
@RequestMapping("/api/user")
public class ProfileController {

    @Autowired
    private UserProfileService profileService;

    @PostMapping("/{id}")
    public ResponseEntity<String> uploadProfilePicture(
            @PathVariable int id,
            @RequestParam("file") MultipartFile file) {

        try {
            profileService.uploadProfilePicture(id, file);
            return ResponseEntity.ok("Profile picture uploaded successfully");
        } catch (IOException | IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable int id) {
        try {
            byte[] profilePicture = profileService.getProfilePicture(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(profilePicture, headers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/create")
    public ResponseEntity<Profile> createUserProfile(@RequestBody Profile userProfile) {
        try {
            Profile createdUserProfile = profileService.createUserProfile(userProfile);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserProfile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

//    @GetMapping("/{username}")
//    public ResponseEntity<Profile> getUserProfileByUsername(@PathVariable String username) {
//        try {
//            Profile userProfile = profileService.getUser(username);
//            return ResponseEntity.ok(userProfile);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @GetMapping("/id/{userId}")
//    public ResponseEntity<UserProfile> getUserProfileById(@PathVariable Long userId) {
//        try {
//            UserProfile userProfile = userProfileService.getUserProfileById(userId);
//            return ResponseEntity.ok(userProfile);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

}
