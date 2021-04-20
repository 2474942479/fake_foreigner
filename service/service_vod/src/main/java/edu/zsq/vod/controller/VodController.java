package edu.zsq.vod.controller;

import edu.zsq.utils.result.JsonResult;
import edu.zsq.vod.service.VodService;
import edu.zsq.vod.service.impl.VodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张
 */
@RestController
@RequestMapping("/eduVod/video")
public class VodController {

    @Resource
    private VodService vodService;

    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @PostMapping("/uploadVideo")
    public JsonResult<String> uploadVideo(MultipartFile file){
        return vodService.uploadVideo(file);
    }


    /**
     * 通过视频资源Id删除阿里云中的视频
     * @param videoSourceId
     */
    @DeleteMapping("/removeVod/{videoSourceId}")
    public JsonResult<Void> removeVod(@PathVariable String videoSourceId){
        return vodService.removeVod(videoSourceId);
    }

    /**
     * 通过多个id批量删除视频
     *
     * @param vodIdList
     * @return
     */
    @DeleteMapping("/removeVodList")
    public JsonResult<Void> removeVodList(@RequestParam List<Integer> vodIdList){
       vodService.removeVodList(vodIdList);
       return JsonResult.OK;
    }


    /**
     * 根据视频id获取视频播放凭证
     * @param videoId
     * @return
     */
    @GetMapping("/getVideoPlayAuth/{videoId}")
    public JsonResult<String> getVideoPlayAuth(@PathVariable String videoId){
        return vodService.getPlayAuth(videoId);
    }

}
