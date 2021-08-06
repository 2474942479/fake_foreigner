package edu.zsq.cms.wrapper;

import com.alibaba.fastjson.JSON;
import edu.zsq.service_edu_api.entity.vo.ChapterVO;
import edu.zsq.service_edu_api.service.ChapterServiceApi;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张
 */
@Component
@Slf4j
public class ChapterServiceWrapper {

    @Resource
    private ChapterServiceApi chapterServiceApi;

    public List<ChapterVO> getAllChapterVO(String courseId) {
        JsonResult<List<ChapterVO>> jsonResult;
        try {
            jsonResult = chapterServiceApi.getAllChapterVO(courseId);
        } catch (Exception e) {
            log.error("调用用户统计接口异常, 参数:{}", courseId, e);
            throw ExFactory.throwSystem("调用用户统计接口异常");
        }

        if (!jsonResult.isSuccess()) {
            log.error("调用用户统计接口失败, 参数: {}, 响应: {}", courseId, JSON.toJSONString(jsonResult));
            throw ExFactory.throwSystem("调用用户统计接口失败");
        }
        return jsonResult.getData();
    }

}
