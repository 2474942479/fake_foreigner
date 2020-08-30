package edu.zsq.eduservice.utils;

import edu.zsq.utils.result.MyResultUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 熔断后才执行该方法
 * @author 张
 */
@Component
public class VodClientImpl  implements VodClient{
    @Override
    public MyResultUtils removeVod(String videoSourceId) {
        return MyResultUtils.error().message("Vod服务器出错了");
    }

    @Override
    public MyResultUtils removeVodList(List<String> vodIdList) {
        return MyResultUtils.error().message("Vod服务器出错了");
    }
}
