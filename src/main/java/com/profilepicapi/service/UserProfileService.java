package com.profilepicapi.service;

import com.profilepicapi.model.Profile;
import com.profilepicapi.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private ProfileRepository userProfileRepository;

    public void uploadProfilePicture(int id, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Please upload a file");
        }

        Optional<Profile> userProfileOptional = userProfileRepository.findById((long) id);
        if (userProfileOptional.isPresent()) {
            Profile userProfile = userProfileOptional.get();

            byte[] profilePictureBytes = file.getBytes();

            userProfile.setProfilePic(profilePictureBytes);

            userProfileRepository.save(userProfile);
        } else {
            throw new EntityNotFoundException("User profile not found for id: " + id);
        }
    }

    public byte[] getProfilePicture(int id) {
        Optional<Profile> userProfileOptional = userProfileRepository.findById((long) id);
        if (userProfileOptional.isPresent()) {
            Profile userProfile = userProfileOptional.get();
            return userProfile.getProfilePic();
        } else {
            throw new EntityNotFoundException("User profile not found for id: " + id);
        }
    }
    public Profile createUserProfile(Profile userProfile) {
        return userProfileRepository.save(userProfile);
    }
}
