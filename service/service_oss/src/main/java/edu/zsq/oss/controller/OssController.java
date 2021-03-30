package edu.zsq.oss.controller;

import edu.zsq.oss.service.OssService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduOss/fileOss")
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     *     上传头像的方法
      */
    @PostMapping("/uploadAvatar")
    public JsonResult UploadOssFile(MultipartFile file) {
//        上传头像返回oss中的图片的url
        StringBuffer url = ossService.uploadAvatar(file);
        return JsonResult.success().data("url",url);
    }

}
