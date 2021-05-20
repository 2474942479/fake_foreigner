package edu.zsq.eduservice.controller;

import edu.zsq.eduservice.service.OssService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

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

    @GetMapping("/")

    @DeleteMapping("/removeBatchOssFile")
    public JsonResult<Void> removeBatchOssFile(@RequestBody List<String> fileUrls) {
        ossService.removeBatchOssFile(fileUrls);
        return JsonResult.success();
    }
}
