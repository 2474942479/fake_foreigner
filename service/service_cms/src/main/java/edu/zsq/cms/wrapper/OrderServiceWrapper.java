package edu.zsq.cms.wrapper;

import com.alibaba.fastjson.JSON;
import edu.zsq.service_order_api.service.OrderServiceApi;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 张
 */
@Component
@Slf4j
public class OrderServiceWrapper {

    @Resource
    private OrderServiceApi orderServiceApi;


    /**
     * 调用订单服务, 查新该用户是否购买该课程
     *
     * @param userId   用户id
     * @param courseId 订单id
     * @return 购买结果
     */
    public Boolean isBuyCourse(String userId, String courseId) {
        JsonResult<Boolean> jsonResult;
        try {
            jsonResult = orderServiceApi.isBuyCourse(userId, courseId);
        } catch (Exception e) {
            log.error("调用用户统计接口异常, 参数: userId = {}, courseId = {}", userId, courseId, e);
            throw ExFactory.throwSystem("调用用户统计接口异常");
        }

        if (!jsonResult.isSuccess()) {
            log.error("调用用户统计接口失败, 参数: userId = {}, courseId = {}, 响应: {}", userId, courseId, JSON.toJSONString(jsonResult));
            throw ExFactory.throwSystem("调用用户统计接口失败");
        }
        return jsonResult.getData();

    }

}
