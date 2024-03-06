package com.pet.s3.S3.Upload.File.uploadingfiles.service;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.Objects;

@Service
@Log4j2
public class AmazonClient {

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;
    private AmazonS3 s3Client;

    @Value("${amazonProperties.region}")
    private String region;

    @Value("${amazonProperties.signingRegion}")
    private String signingRegion;

    @Value("${amazonProperties.publicUrl}")
    private String publicUrl;

    @PostConstruct
    private void initializeAmazon() {
        log.info("---------------------------------");
        log.info("Initializing Connect CloudFlare bucket name: " + bucketName);
        log.info("---------------------------------");
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(endpointUrl, signingRegion))
                .build();
    }

    public String uploadFile(MultipartFile multipartFile)
             {
        File file = convertMultipartToFile(multipartFile);
        String fileName = generateFileName(multipartFile);
//        String fileUrl = endpointUrl.replace("$1", bucketName).replace("$2", region) + "/" + fileName;
//        log.info("Uploading file to AWS S3: " + fileUrl);
        return uploadFileToS3Bucket(bucketName, fileName, file);
    }

    private String uploadFileToS3Bucket(String bucketName, String fileName, File file) {
        s3Client.putObject(
                new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        log.info("Public Url image to S3: " + publicUrl + fileName);
        return publicUrl + fileName;
    }

    @SneakyThrows
    private File convertMultipartToFile(MultipartFile file)  {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }
}
