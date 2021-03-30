package edu.zsq.cms.frign;

import edu.zsq.utils.result.JsonResult;
import org.springframework.stereotype.Component;

/**
 * @author 张
 */
@Component
public class ChapterServiceImpl implements ChapterService{
    @Override
    public JsonResult getAllChapterVo(String courseId) {
        return JsonResult.failure().message("edu服务器出错了");
    }
}
