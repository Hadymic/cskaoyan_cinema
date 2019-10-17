package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.cskaoyan.cinema.rest.config.OssConfig;
import com.cskaoyan.cinema.service.OssService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Service(interfaceClass = OssService.class)
public class OssServiceImpl implements OssService {
    @Autowired
    private OssConfig ossConfig;

    @Autowired
    private OSSClient ossClient;

    /**
     * 将文件上传至阿里云oss，并返回访问前缀
     *
     * @param file
     * @return
     */
    @Override
    public String upload(File file) {
        String key = file.getName();
        ossClient.putObject(new PutObjectRequest(ossConfig.getBucket(), key, file));
        return ossConfig.getImgPre();
    }
}
