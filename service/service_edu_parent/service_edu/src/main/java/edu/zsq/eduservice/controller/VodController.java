package edu.zsq.eduservice.controller;

import edu.zsq.eduservice.service.VodService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张
 */
@RestController
@RequestMapping("/eduService/vod")
public class VodController {

    @Resource
    private VodService vodService;

    /**
     * 上传视频到阿里云
     *
     * @param file 视频文件
     * @return 阿里云视频id
     */
    @PostMapping("/uploadVideo")
    public JsonResult<String> uploadVideo(MultipartFile file) {
        return JsonResult.success(vodService.uploadVideo(file));
    }


    /**
     * 通过视频资源Id删除阿里云中的视频
     *
     * @param videoSourceId 阿里云视频id
     */
    @DeleteMapping("/removeVod/{videoSourceId}")
    public JsonResult<Void> removeVod(@PathVariable String videoSourceId) {
        vodService.removeVod(videoSourceId);
        return JsonResult.OK;
    }

    /**
     * 通过多个id批量删除视频
     *
     * @param vodIds 视频id
     */
    @DeleteMapping("/removeVodList")
    public JsonResult<Void> removeVodList(@RequestParam List<String> vodIds) {
        vodService.removeVodList(vodIds);
        return JsonResult.OK;
    }


    /**
     * 根据视频id获取视频播放凭证
     *
     * @param videoId 视频id
     * @return 播放凭证
     */
    @GetMapping("/getVideoPlayAuth/{videoId}")
    public JsonResult<String> getVideoPlayAuth(@PathVariable String videoId) {
        return JsonResult.success(vodService.getPlayAuth(videoId));
    }
}

