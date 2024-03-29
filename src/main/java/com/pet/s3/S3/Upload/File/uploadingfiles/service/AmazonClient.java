package com.pet.s3.S3.Upload.File.uploadingfiles.service;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringUtils;
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

    @Value("${amazonProperties.enabled}")
    private boolean enabled;
    @PostConstruct
    private void initializeAmazon() {
        if (enabled) {
            log.info("---------------------------------");
            log.info("Initializing Connect CloudFlare bucket name: " + bucketName);
            log.info("---------------------------------");
            AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
            this.s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(endpointUrl, signingRegion)).build();
        }else{
            log.info("---------------------------------");
            log.info("Initializing Connect CloudFlare bucket : " + bucketName + " disabled");
            log.info("---------------------------------");
        }
    }

    public String uploadFile(String folder, MultipartFile multipartFile) {
        File file = convertMultipartToFile(multipartFile);
        String fileName = ( StringUtils.isNullOrEmpty(folder) ? "" : (folder + "/") )+ generateFileName(multipartFile);
        return uploadFileToS3Bucket(bucketName, fileName, file);
    }

    private String uploadFileToS3Bucket(String bucketName, String fileName, File file) {
        try {
            if (enabled) {
                s3Client.putObject(
                        new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
                log.info("Public Url image to S3: " + publicUrl + fileName);
                return publicUrl + fileName;
            } else {
                log.debug("Public Url image to S3: " + publicUrl + fileName);
                return "";
            }
        } catch (Exception e) {
            log.debug("Public Url image to S3: " + publicUrl + fileName);
            return "";
        }
    }

    @SneakyThrows
    private File convertMultipartToFile(MultipartFile file) {
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
