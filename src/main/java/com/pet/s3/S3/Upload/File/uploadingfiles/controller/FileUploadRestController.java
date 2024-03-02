//package com.pet.s3.S3.Upload.File.uploadingfiles.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.GetUrlRequest;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.util.Objects;
//
//@RestController
////@Api(" API v1  File Upload")
////@Tag(name = "File Upload API v1")
//@RequestMapping(value = "/api/v1/admin/file")
//public class FileUploadRestController {
//
//    @PostMapping("/uploadFile")
//    @ResponseBody
//    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
//        try {
//            String bucketName = "https://ec0b88e59da9d8ab7d05c20afef02f04.r2.cloudflarestorage.com/cho-mua-sam";
//            S3Client s3Client = S3Client.builder()
//                    .region(Region.AF_SOUTH_1) // Replace with your desired region
//                    .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
//                    .build();
//
//            File convertedFile = convertMultiPartFileToFile(file);
//
//            s3Client.putObject(PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(file.getOriginalFilename())
//                    .build(), RequestBody.fromFile(convertedFile));
//
//            URL fileUrl = s3Client.utilities().getUrl(GetUrlRequest.builder()
//                    .bucket(bucketName)
//                    .key(file.getOriginalFilename())
//                    .build());
//
//            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully! URL: " + fileUrl.toString());
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
//        }
//    }
//    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
//        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
//        file.transferTo(convertedFile);
//        return convertedFile;
//    }
//
//}
