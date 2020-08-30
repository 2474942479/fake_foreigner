package edu.zsq.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zsq.order.entity.Order;
import edu.zsq.order.mapper.OrderMapper;
import edu.zsq.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.utils.exception.servicexception.MyException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    /**
     * 判断用户是否购买课程
     * @param userId 用户id
     * @param courseId  课程id
     * @return
     */
    @Override
    public boolean isBuyCourse(String userId, String courseId) {

        if (userId ==null || "".equals(userId)){
            throw new MyException(28004,"您尚未登录，请先登录");
        }
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("course_id",courseId);
        wrapper.eq("status",1);
        Integer count = baseMapper.selectCount(wrapper);
        if (count>0) {
            return true;
        }else{
            return false;
        }
    }
}
