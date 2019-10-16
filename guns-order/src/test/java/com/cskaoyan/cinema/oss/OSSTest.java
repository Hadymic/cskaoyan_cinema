package com.cskaoyan.cinema.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.Test;

import java.io.File;
import java.util.UUID;

public class OSSTest {

    @Test
    public void test1() {
        String bucket = "cskaoyan-cinema";
        String endPoint = "oss-cn-shanghai.aliyuncs.com";
        String accessKeyId = "LTAI4FfWGmJfGug6ekaEHw4v";
        String accessSecret = "C6k2x76D2LuULywLoHMFz7YTzu8Aku";
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessSecret);
        File myfile = new File("D:\\tmp\\qr-tradeprecreate1571216057973905377.png");
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String key = uuid + "-123456.png";
        ossClient.putObject(new PutObjectRequest(bucket, key, myfile));
    }
}
