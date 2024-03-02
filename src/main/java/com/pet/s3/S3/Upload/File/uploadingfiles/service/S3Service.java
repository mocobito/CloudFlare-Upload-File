//package com.pet.s3.S3.Upload.File.uploadingfiles.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Objects;
//
//@Service
//public class S3Service {
//
//    @Value("${aws.s3.bucket-name}")
//    private String bucketName;
//
//    @Value("${aws.s3.bucket-region}")
//    private String bucketRegion;
//
//    public void uploadFile(MultipartFile file) throws IOException {
//        S3Client s3Client = S3Client.builder()
//                .region(Region.of(bucketRegion))
//                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
//                .build();
//
//        String fileName = file.getOriginalFilename();
//        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                .bucket(bucketName)
//                .key(fileName)
//                .build();
//
//        s3Client.putObject(putObjectRequest, RequestBody.fromFile(convertMultipartFileToFile(file)));
//    }
//
//    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
//        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
//        file.transferTo(convertedFile);
//        return convertedFile;
//    }
//}
