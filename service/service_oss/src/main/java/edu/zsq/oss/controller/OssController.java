package edu.zsq.oss.controller;

import edu.zsq.oss.service.OssService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author 张
 */
@RestController
@RequestMapping("/eduOss/fileOss")
public class OssController {

    @Resource
    private OssService ossService;

    /**
     * 上传至阿里云OSS
     *
     * @param file 文件
     * @return
     */
    @PostMapping("/uploadAvatar")
    public JsonResult<String> uploadOssFile(MultipartFile file) {
        return JsonResult.success(ossService.uploadAvatar(file));
    }

}
