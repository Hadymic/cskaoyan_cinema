package com.cskaoyan.cinema.rest.config;

import com.aliyun.oss.OSSClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cinema.aliyun.oss")
public class OssConfig {
    String accessKeyId;
    String accessSecret;
    String bucket;
    String endPoint;

    @Bean
    public OSSClient ossClient() {
        return new OSSClient(endPoint, accessKeyId, accessSecret);
    }

    public String getImgPre() {
        return "https://" + bucket + "." + endPoint + "/";
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
}
