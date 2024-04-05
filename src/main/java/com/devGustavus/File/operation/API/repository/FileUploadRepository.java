package com.devGustavus.File.operation.API.repository;

import com.devGustavus.File.operation.API.entity.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, String> {

}
