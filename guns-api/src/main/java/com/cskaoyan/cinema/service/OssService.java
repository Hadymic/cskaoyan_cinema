package com.cskaoyan.cinema.service;

import java.io.File;

/**
 * oss Service层
 * @author Hadymic
 */
public interface OssService {
    /**
     * 将文件上传至阿里云oss，并返回访问前缀
     *
     * @param file
     * @return
     */
    String upload(File file);
}
