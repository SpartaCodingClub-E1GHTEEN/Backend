package com.sparta.first.project.eighteen.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3ManageConfig {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 이미지 파일 업로드 메서드
     * @param file : 업로드할 파일
     * @return : s3에 저장된 경로
     * @throws IOException : 입출력 예외
     */
    public String uploadImage(MultipartFile file) throws IOException {
        // 랜덤 파일
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        // AWS S3에 업로드
        amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);

        // 업로드된 파일의 S3 URL 반환
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    /**
     * 이미지 파일 삭제 메서드
     * @param originFileName : 원래의 이미지 파일명
     * @throws IOException : 입출력 예외
     */
    public void deleteImage(String originFileName) throws IOException {
        String fileName = originFileName.split("/")[3];
        amazonS3.deleteObject(bucket, fileName);
    }


}