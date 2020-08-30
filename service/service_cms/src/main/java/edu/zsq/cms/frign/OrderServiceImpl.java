package edu.zsq.cms.frign;

import org.springframework.stereotype.Component;

/**
 * @author 张
 */
@Component
public class OrderServiceImpl implements OrderService{

    /**
     * 判断用户是否购买课程
     * 用户未登录 抛异常HystrixRuntimeException:OrderService#isBuyCourse(String,String) failed and fallback failed.
     * 解决 直接返回false
     * @param userId 用户id
     * @param courseId  课程id
     * @return
     */
    @Override
    public Boolean isBuyCourse(String userId, String courseId) {

        return false;
    }
}
