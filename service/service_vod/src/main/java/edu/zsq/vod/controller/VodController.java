package edu.zsq.vod.controller;

import edu.zsq.utils.result.MyResultUtils;
import edu.zsq.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 张
 */
@RestController
@RequestMapping("/eduVod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @PostMapping("/uploadVideo")
    public MyResultUtils uploadVideo(MultipartFile file){

        String videoSourceId = vodService.uploadVideo(file);
        return MyResultUtils.ok().data("videoSourceId",videoSourceId);
    }


    /**
     * 通过视频资源Id删除阿里云中的视频
     * @param videoSourceId
     */
    @DeleteMapping("/removeVod/{videoSourceId}")
    public MyResultUtils removeVod(@PathVariable String videoSourceId){
        boolean remove = vodService.removeVod(videoSourceId);
        if (remove){
            return MyResultUtils.ok().message("删除视频成功");

        }else{
            return MyResultUtils.ok().message("删除视频失败");
        }
    }

    /**
     * 通过多个id批量删除视频
     *
     * @param vodIdList
     * @return
     */
    @DeleteMapping("/removeVodList")
    public MyResultUtils removeVodList(@RequestParam List vodIdList){

        boolean remove = vodService.removeVodList(vodIdList);
        if (remove){
            return MyResultUtils.ok().message("删除视频成功");

        }else{
            return MyResultUtils.ok().message("删除视频失败");
        }
    }


    /**
     * 根据视频id获取视频播放凭证
     * @param videoId
     * @return
     */
    @GetMapping("/getVideoPlayAuth/{videoId}")
    public MyResultUtils getVideoPlayAuth(@PathVariable String videoId){

        String playAuth = vodService.getPlayAuth(videoId);

        return MyResultUtils.ok().data("playAuth",playAuth);
    }

}
