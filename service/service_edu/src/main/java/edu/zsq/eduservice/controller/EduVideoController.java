package edu.zsq.eduservice.controller;


import edu.zsq.eduservice.entity.EduVideo;
import edu.zsq.eduservice.entity.dto.VideoDTO;
import edu.zsq.eduservice.entity.vo.EduVideoVO;
import edu.zsq.eduservice.entity.vo.chapter.VideoVO;
import edu.zsq.eduservice.service.EduVideoService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 添加小节
     *
     * @param videoDTO 小节信息
     * @return 添加结果
     */
    @PostMapping("/saveVideo")
    public JsonResult<Void> saveVideo(@RequestBody VideoDTO videoDTO) {
        return eduVideoService.saveVideo(videoDTO);
    }

    /**
     * 删除小节并调用service_vod服务的删除阿里云上的视频
     *
     * @param id 小节id
     * @return 删除结果
     */
    @DeleteMapping("{id}")
    public JsonResult<Void> deleteVideo(@PathVariable String id) {
        return eduVideoService.removeVideoAndVodById(id);
    }

    /**
     * 更新小节
     *
     * @param videoDTO 小节信息
     * @return 更新结果
     */
    @PutMapping("/updateVideo")
    public JsonResult<Void> updateVideo(@RequestBody VideoDTO videoDTO) {
        return eduVideoService.updateVideo(videoDTO);
    }

    /**
     * 根据id获取小节信息
     *
     * @param id 小节id
     * @return 小节信息
     */
    @GetMapping("/getVideo/{id}")
    public JsonResult<EduVideoVO> getVideo(@PathVariable String id) {
        return JsonResult.success(eduVideoService.getVideo(id));
    }

}

