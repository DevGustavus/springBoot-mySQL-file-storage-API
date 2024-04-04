package com.devGustavus.File.operation.API.controller;

import com.devGustavus.File.operation.API.service.FileStorageService;
import com.devGustavus.File.operation.API.entity.ImageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class FileOperationController {

    @Autowired
    FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        ImageData uploadedImageData = fileStorageService.uploadImage(file);

        return ResponseEntity.status(HttpStatus.OK).body("Saved Image in DB with name: " + uploadedImageData.getName());
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String filename) {
        Optional<byte[]> imageDataOptional = fileStorageService.downloadImage(filename);

        if (imageDataOptional.isPresent()) {
            byte[] imageData = imageDataOptional.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
