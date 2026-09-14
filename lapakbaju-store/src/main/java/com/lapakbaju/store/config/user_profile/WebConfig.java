package com.lapakbaju.store.config.user_profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload.user-profile:D:/xampp/htdocs/lapakbaju/images/profiles/}")
    private String profileUploadDir;

    @Value("${file.upload.product:D:/xampp/htdocs/lapakbaju/images/products/}")
    private String productUploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Format paths into valid file URIs (e.g. file:///D:/xampp/htdocs/lapakbaju/images/profiles/)
        String profilePath = formatFileUri(profileUploadDir);
        String productPath = formatFileUri(productUploadDir);

        // Serve profile images: maps /uploads/profile/** -> D:/xampp/htdocs/lapakbaju/images/profiles/
        registry.addResourceHandler("/uploads/profile/**")
                .addResourceLocations(profilePath);

        // Serve product images: maps /uploads/product/** -> D:/xampp/htdocs/lapakbaju/images/products/
        registry.addResourceHandler("/uploads/product/**")
                .addResourceLocations(productPath);
    }

    private String formatFileUri(String dirPath) {
        if (dirPath == null || dirPath.trim().isEmpty()) {
            return "";
        }
        if (dirPath.startsWith("file:")) {
            return dirPath;
        }
        // Replace Windows backslashes with forward slashes
        String normalized = dirPath.replace("\\", "/");
        // Ensure path ends with a trailing slash
        if (!normalized.endsWith("/")) {
            normalized += "/";
        }
        return "file:///" + normalized;
    }
}