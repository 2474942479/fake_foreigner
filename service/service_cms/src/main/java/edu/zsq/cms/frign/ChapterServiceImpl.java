package edu.zsq.cms.frign;

import edu.zsq.utils.result.MyResultUtils;
import org.springframework.stereotype.Component;

/**
 * @author 张
 */
@Component
public class ChapterServiceImpl implements ChapterService{
    @Override
    public MyResultUtils getAllChapterVo(String courseId) {
        return MyResultUtils.error().message("edu服务器出错了");
    }
}
