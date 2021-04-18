package edu.zsq.cms.wrapper;

import edu.zsq.eduservice.entity.vo.chapter.ChapterVO;
import edu.zsq.utils.result.JsonResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 张
 */
@Component
public class ChapterServiceImpl implements EduCourseServiceWrapper{
    @Override
    public JsonResult<List<ChapterVO>> getAllChapterVo(String courseId) {
        return JsonResult.failure("edu服务器出错了");
    }
}
