package edu.zsq.eduservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /**
     * 批量删除OSS中的文件
     *
     * @param fileUrls 文件路径
     */
    void removeBatchOssFile(List<String> fileUrls);
}
