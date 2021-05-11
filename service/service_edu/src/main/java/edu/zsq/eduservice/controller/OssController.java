package edu.zsq.eduservice.controller;

import edu.zsq.eduservice.service.OssService;
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
@RequestMapping("/eduService/oss")
public class OssController {

    @Resource
    private OssService ossService;

    /**
     * 上传文件至阿里云OSS
     *
     * @param file 文件
     * @return 文件URL
     */
    @PostMapping("/upload2Oss")
    public JsonResult<String> upload2Oss(MultipartFile file) {
        return JsonResult.success(ossService.upload2Oss(file));
    }

}
