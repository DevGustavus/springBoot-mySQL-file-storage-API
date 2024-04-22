package com.devGustavus.File.operation.API.controller;

import com.devGustavus.File.operation.API.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    @Autowired
    FileUploadService fileStorageService;

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        String uploadFile = fileStorageService.uploadFile(file);

        return ResponseEntity.status(HttpStatus.OK).body(uploadFile);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename){
        byte[] fileInbytes = fileStorageService.downloadFile(filename);

        MediaType mediaType = MediaTypeFactory.getMediaType(filename)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType).body(fileInbytes);
    }
}
