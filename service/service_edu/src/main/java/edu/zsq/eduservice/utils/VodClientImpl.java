package edu.zsq.eduservice.utils;

import edu.zsq.utils.result.JsonResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 熔断后才执行该方法
 * @author 张
 */
@Component
public class VodClientImpl  implements VodClient{
    @Override
    public JsonResult removeVod(String videoSourceId) {
        return JsonResult.failure().message("Vod服务器出错了");
    }

    @Override
    public JsonResult removeVodList(List<String> vodIdList) {
        return JsonResult.failure().message("Vod服务器出错了");
    }
}
