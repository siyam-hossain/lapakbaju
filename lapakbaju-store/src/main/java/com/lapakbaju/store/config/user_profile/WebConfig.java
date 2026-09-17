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
        String profilePath = formatFileUri(profileUploadDir);
        String productPath = formatFileUri(productUploadDir);

        // Map http://localhost:8080/images/profiles/** -> D:/xampp/htdocs/lapakbaju/images/profiles/
        registry.addResourceHandler("/images/profiles/**")
                .addResourceLocations(profilePath);

        // Map http://localhost:8080/images/products/** -> D:/xampp/htdocs/lapakbaju/images/products/
        registry.addResourceHandler("/images/products/**")
                .addResourceLocations(productPath);
    }

    private String formatFileUri(String dirPath) {
        if (dirPath == null || dirPath.trim().isEmpty()) {
            return "";
        }
        if (dirPath.startsWith("file:")) {
            return dirPath;
        }
        String normalized = dirPath.replace("\\", "/");
        if (!normalized.endsWith("/")) {
            normalized += "/";
        }
        return "file:///" + normalized;
    }
}