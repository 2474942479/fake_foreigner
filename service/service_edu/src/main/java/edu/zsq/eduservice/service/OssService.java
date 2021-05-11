package edu.zsq.eduservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 张
 */
public interface OssService {

    /**
     * 上传文件到阿里云OSS
     *
     * @param file 上传的文件
     * @return 文件URL
     */
    String upload2Oss(MultipartFile file);
}
