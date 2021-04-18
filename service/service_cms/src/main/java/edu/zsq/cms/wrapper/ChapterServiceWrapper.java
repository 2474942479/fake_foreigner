package edu.zsq.cms.wrapper;

import edu.zsq.eduservice.entity.vo.chapter.ChapterVO;
import edu.zsq.utils.result.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 张
 */
@Component
@FeignClient(name = "service8001-edu",fallback = ChapterServiceImpl.class)
public interface ChapterServiceWrapper {

    /**
     * 根据课程id获取课程大纲列表
     * @param courseId 课程id
     * @return 大纲列表
     *
     * @GetMapping中要写服务全路径----------------------------------------------------------------
     */
    @GetMapping("/eduService/chapter/getAllChapterVo/{courseId}")
    JsonResult<List<ChapterVO>> getAllChapterVO(@PathVariable("courseId") String courseId);
}
