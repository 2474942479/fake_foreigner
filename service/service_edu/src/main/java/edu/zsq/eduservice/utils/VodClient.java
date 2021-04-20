package edu.zsq.eduservice.utils;

import edu.zsq.utils.result.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * feign
 * name: 调用的服务名
 * fallback 出错后调用的类
 * @author 张
 */
@Component
@FeignClient(name = "service8003-vod",fallback = VodClientImpl.class)
public interface VodClient {
    /**
     * 定义调用的方法路径
     * 注解@PathVariable 一定要加参数名称
     * @param videoSourceId
     * @return JsonResult
     */
    @DeleteMapping("/eduVod/video/removeVod/{videoSourceId}")
    JsonResult<Void> removeVod(@PathVariable("videoSourceId") String videoSourceId);

    /**
     * 根据视频id删除多个视频
     * @param vodIdList 视频id集合
     * @return JsonResult
     */
    @DeleteMapping("/eduVod/video/removeVodList")
    JsonResult<Void> removeVodList(@RequestParam("vodIdList") List<String> vodIdList);
}
