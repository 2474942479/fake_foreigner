package edu.zsq.eduservice.provider;

import edu.zsq.eduservice.service.EduChapterService;
import edu.zsq.service_edu_api.entity.vo.ChapterVO;
import edu.zsq.service_edu_api.service.ChapterServiceApi;
import edu.zsq.utils.result.JsonResult;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张
 */
@RestController
public class ChapterServiceProvider implements ChapterServiceApi {

    @Resource
    private EduChapterService chapterService;

    @Override
    public JsonResult<List<ChapterVO>> getAllChapterVO(String courseId) {
        return JsonResult.success(chapterService.getAllChapterVO(courseId));
    }
}
