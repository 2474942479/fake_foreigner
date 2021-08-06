package edu.zsq.service_edu_api.service;

import edu.zsq.service_edu_api.entity.vo.ChapterVO;
import edu.zsq.utils.result.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 张
 */
@FeignClient(value = "service8001-edu", path = "/")
@Component
public interface ChapterServiceApi {

    /**
     * 根据课程id获取课程大纲列表
     *
     * @param courseId 课程id
     * @return 大纲列表
     */
    @GetMapping("/eduService/chapter/getAllChapterVO/{courseId}")
    JsonResult<List<ChapterVO>> getAllChapterVO(@PathVariable("courseId") String courseId);
}
