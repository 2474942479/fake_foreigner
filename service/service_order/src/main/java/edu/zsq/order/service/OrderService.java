package edu.zsq.order.service;

import edu.zsq.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-25
 */
public interface OrderService extends IService<Order> {


    /**
     * 判断用户是否购买课程
     * @param userId 用户id
     * @param courseId  课程id
     * @return
     */

    boolean isBuyCourse(String userId, String courseId);
}
