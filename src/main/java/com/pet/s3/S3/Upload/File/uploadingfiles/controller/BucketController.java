package com.pet.s3.S3.Upload.File.uploadingfiles.controller;

import com.pet.s3.S3.Upload.File.uploadingfiles.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/storage/")
public class BucketController {
    @Autowired
    private AmazonClient amazonClient;


    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file){
        return this.amazonClient.uploadFile(file);
    }
}
