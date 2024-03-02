//package com.pet.s3.S3.Upload.File.uploadingfiles.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//// Controller for file upload
//@RestController
//@RequestMapping("/api/files")
//public class FileUploadController {
//
//    @Autowired
//    private AmazonS3 amazonS3;
//
//    @Value("${aws.s3.bucket-name}")
//    private String bucketName;
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
//        try {
//            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType(file.getContentType());
//            amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
//            return ResponseEntity.ok("File uploaded successfully!");
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
//        }
//    }
//}
