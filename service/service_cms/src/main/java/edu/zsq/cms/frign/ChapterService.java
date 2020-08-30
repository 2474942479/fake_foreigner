package edu.zsq.cms.frign;

import edu.zsq.utils.result.MyResultUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 张
 */
@Component
@FeignClient(name = "service8001-edu",fallback = ChapterServiceImpl.class)
public interface ChapterService {

    /**
     * 根据课程id获取课程大纲列表
     * @param courseId
     * @return
     *
     * @GetMapping中要写服务全路径----------------------------------------------------------------
     */
    @GetMapping("/eduService/chapter/getAllChapterVo/{courseId}")
    MyResultUtils getAllChapterVo(@PathVariable("courseId") String courseId);
}
