package com.lapakbaju.store.controller.user_profile;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.entity.user_profile.UserAddressEntity;
import com.lapakbaju.store.repository.authentication.UserRepository;
import com.lapakbaju.store.repository.user_profile.UserAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.UUID;

@Controller
public class EditUserProfileController {

    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;

    @Value("${file.upload.user-profile}")
    private String userProfilePath;

    // Constructor Injection (Recommended by Spring)
    @Autowired
    public EditUserProfileController(UserRepository userRepository, UserAddressRepository userAddressRepository) {
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
    }

    @GetMapping("/edit/profile")
    public String editProfile(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        String email = principal.getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);

        return "user_profile_fragment/edit-user-profile";
    }

    @PostMapping("/edit/profile")
    public String updateProfile(
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam("addressLine") String addressLine,
            @RequestParam("city") String city,
            @RequestParam(value = "postalCode", required = false) String postalCode,
            @RequestParam("country") String country,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        if (principal == null) {
            return "redirect:/login";
        }

        String email = principal.getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1. Update or create User Address
        UserAddressEntity address = user.getAddress();
        if (address == null) {
            address = new UserAddressEntity();
        }
        address.setAddressLine(addressLine);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setCountry(country);

        userAddressRepository.save(address);
        user.setAddress(address);

        // 2. Handle Profile Image Upload
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                Path uploadPath = Paths.get(userProfilePath);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Generate unique filename to prevent collisions
                String originalFilename = profileImage.getOriginalFilename();
                String extension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String newFilename = UUID.randomUUID().toString() + extension;

                // Save file to disk
                Path targetFilePath = uploadPath.resolve(newFilename);
                Files.copy(profileImage.getInputStream(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);

                // Remove existing old profile picture from directory
                if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
                    Path oldImagePath = uploadPath.resolve(user.getProfileImage());
                    Files.deleteIfExists(oldImagePath);
                }

                user.setProfileImage(newFilename);

            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Failed to upload profile picture.");
                return "redirect:/edit/profile";
            }
        }

        // 3. Save User updates
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");

        return "redirect:/user-profile";
    }
}