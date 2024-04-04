package com.devGustavus.File.operation.API.service;

import com.devGustavus.File.operation.API.entity.ImageData;
import com.devGustavus.File.operation.API.repository.ImageDataStorageRepository;
import com.devGustavus.File.operation.API.utility.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileStorageService {

    @Autowired
    ImageDataStorageRepository imageDataStorageRepository;

    public ImageData uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = new ImageData();
        imageData.setName(file.getOriginalFilename());
        imageData.setType(file.getContentType());
        imageData.setImageData(ImageUtils.compressImage(file.getBytes()));
        return imageDataStorageRepository.saveAndFlush(imageData);
    }

    public Optional<byte[]> downloadImage(String fileName) {
        Optional<ImageData> imageDataOptional = imageDataStorageRepository.findByName(fileName);
        return imageDataOptional.map(imageData -> ImageUtils.decompressImage(imageData.getImageData()));
    }

}
