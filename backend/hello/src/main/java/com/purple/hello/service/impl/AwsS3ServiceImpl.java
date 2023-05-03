package com.purple.hello.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.purple.hello.dto.tool.AwsS3DTO;
import com.purple.hello.service.AwsS3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AwsS3ServiceImpl implements AwsS3Service {
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public AwsS3ServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }
    public AwsS3DTO upload(MultipartFile file, String dirName) throws IOException {
        String key = randomFileName(file, dirName);
        String path = putS3(file, key);

        return AwsS3DTO
                .builder()
                .key(key)
                .path(path)
                .build();
    }

    private String randomFileName(MultipartFile file, String dirName) {
        return dirName + "/" + UUID.randomUUID() + file.getOriginalFilename();
    }

    private String putS3(MultipartFile uploadFile, String fileName) throws IOException {
        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(uploadFile.getContentType());
        metadata.setContentLength(uploadFile.getSize());
        amazonS3.putObject(bucket,fileName,uploadFile.getInputStream(), metadata);
        return getS3(bucket, fileName);
    }

    private String getS3(String bucket, String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public boolean remove(AwsS3DTO awsS3DTO) throws AmazonS3Exception{
        if (!amazonS3.doesObjectExist(bucket, awsS3DTO.getKey())) {
            return false;
        }else {
            amazonS3.deleteObject(bucket, awsS3DTO.getKey());
            return true;
        }
    }
}
