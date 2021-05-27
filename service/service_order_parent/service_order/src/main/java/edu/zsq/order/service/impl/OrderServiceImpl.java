package edu.zsq.order.service.impl;

import edu.zsq.order.entity.Order;
import edu.zsq.order.mapper.OrderMapper;
import edu.zsq.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.utils.exception.core.ExFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    /**
     * 判断用户是否购买课程
     * @param userId 用户id
     * @param courseId  课程id
     * @return 购买结果
     */
    @Override
    public boolean isBuyCourse(String userId, String courseId) {

        if (StringUtils.isEmpty(userId)){
            throw ExFactory.throwBusiness("您尚未登录，请先登录");
        }

        return lambdaQuery()
                .eq(Order::getUserId, userId)
                .eq(Order::getCourseId, courseId)
                .eq(Order::getStatus, 1)
                .count() > 0;
    }
}
