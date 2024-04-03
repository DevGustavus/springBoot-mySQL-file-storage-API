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

    public String uploadImage(MultipartFile file) throws IOException {
        ImageData result = imageDataStorageRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());

        if(result != null){
            return "Saved Image in DB with name: "+file.getOriginalFilename();
        }
        return "Ohh ...Image not save ..";


    }


    public byte[] downloadImage(String fileName){
        Optional<ImageData> imageFromDb = imageDataStorageRepository.findByName(fileName);
        byte[] imageInbytes = ImageUtils.decompressImage(imageFromDb.get().getImageData());
        return imageInbytes;
    }

}
