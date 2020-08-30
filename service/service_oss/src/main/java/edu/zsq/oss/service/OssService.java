package edu.zsq.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 张
 */
public interface OssService {
    /**
     * 上传头像到oss
     *
     * @param file 上传的文件
     * @return StringBuffer类型的拼接后的url  线程安全变长的字符串
     */
    StringBuffer uploadAvatar(MultipartFile file);
}
