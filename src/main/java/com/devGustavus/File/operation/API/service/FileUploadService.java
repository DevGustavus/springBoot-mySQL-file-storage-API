package com.devGustavus.File.operation.API.service;

import com.devGustavus.File.operation.API.entity.FileUpload;
import com.devGustavus.File.operation.API.repository.FileUploadRepository;
import com.devGustavus.File.operation.API.utility.FileUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FileUploadService {

    @Autowired
    FileUploadRepository fileUploadRepository;

    public String uploadFile(MultipartFile file) throws IOException {
        FileUpload result = fileUploadRepository.save(FileUpload.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .fileData(FileUtils.compressFile(file.getBytes())).build());

        if(result != null){
            return "Saved File in DB with name: "+file.getOriginalFilename();
        }
        return "Ohh ...File not save ..";
    }


    public byte[] downloadFile(String fileName){
        Optional<FileUpload> fileFromDb = fileUploadRepository.findByName(fileName);
        byte[] fileInbytes = FileUtils.decompressFile(fileFromDb.get().getFileData());
        return fileInbytes;
    }

}
