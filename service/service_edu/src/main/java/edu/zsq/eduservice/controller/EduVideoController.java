package edu.zsq.eduservice.controller;


import edu.zsq.eduservice.entity.EduVideo;
import edu.zsq.eduservice.service.EduVideoService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-16
 */
@RestController
@RequestMapping("/eduService/video")
public class EduVideoController {
    @Resource
    private EduVideoService eduVideoService;


    /**
     *
     * 添加小节
     * @param video
     * @return
     */
    @PostMapping("/saveVideo")
    public JsonResult saveVideo(@RequestBody EduVideo video){

        boolean save = eduVideoService.save(video);
        if (save){
            return JsonResult.success().message("添加小节成功");
        }else {
            return JsonResult.success().message("添加小节失败");
        }

    }

    /**
     *
     * 删除小节并通过nacos调用service_vod服务的删除阿里云上的视频
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public JsonResult deleteVideo(@PathVariable String id){

        boolean remove = eduVideoService.removeVideoAndVodById(id);

        if (remove){
            return JsonResult.success().message("删除小节成功");
        }else {
            return JsonResult.failure().message("删除小节失败");
        }

    }

    /**
     * 更新小节
     * @param video
     * @return
     */
    @PutMapping("/updateVideo")
    public JsonResult updateVideo(@RequestBody EduVideo video){
        boolean update = eduVideoService.updateById(video);
        if (update){
            return JsonResult.success().message("更新小节成功");
        }else {
            return JsonResult.success().message("更新小节失败");
        }
    }

    /**
     * 根据小节id获取小节信息
     * @param id
     * @return
     */
    @GetMapping("/getVideo/{id}")
    public JsonResult getVideo(@PathVariable String id){
        EduVideo video = eduVideoService.getById(id);

        return JsonResult.success().data("videoInfo",video);
    }

}

