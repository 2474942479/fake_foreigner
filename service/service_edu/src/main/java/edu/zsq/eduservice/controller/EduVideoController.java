package edu.zsq.eduservice.controller;


import edu.zsq.eduservice.entity.dto.VideoDTO;
import edu.zsq.eduservice.entity.vo.VideoInfoVO;
import edu.zsq.eduservice.service.EduVideoService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/eduService/video")
public class EduVideoController {
    @Resource
    private EduVideoService eduVideoService;

    /**
     * 添加或修改小节
     *
     * @param videoDTO 小节信息
     * @return 添加结果
     */
    @PostMapping("/saveOrUpdateVideo")
    public JsonResult<Void> saveOrUpdateVideo(@RequestBody VideoDTO videoDTO) {
        eduVideoService.saveOrUpdateVideo(videoDTO);
        return JsonResult.OK;
    }

    /**
     * 删除小节并调用service_vod服务的删除阿里云上的视频
     *
     * @param id 小节id
     * @return 删除结果
     */
    @DeleteMapping("removeAllVideo/{id}")
    public JsonResult<Void> deleteVideo(@PathVariable String id) {
        eduVideoService.removeVideoAndVodById(id);
        return JsonResult.OK;
    }

    /**
     * 根据id获取小节信息
     *
     * @param id 小节id
     * @return 小节信息
     */
    @GetMapping("/getVideoInfo/{id}")
    public JsonResult<VideoInfoVO> getVideoInfo(@PathVariable String id) {
        return JsonResult.success(eduVideoService.getVideoInfo(id));
    }

}

