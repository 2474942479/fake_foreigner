package edu.zsq.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.order.common.enums.OrderStatusEnum;
import edu.zsq.order.entity.Order;
import edu.zsq.order.entity.dto.OrderDTO;
import edu.zsq.order.entity.dto.OrderQueryDTO;
import edu.zsq.order.mapper.OrderMapper;
import edu.zsq.order.service.OrderService;
import edu.zsq.order.utils.OrderNumberUtil;
import edu.zsq.service_order_api.entity.OrderVO;
import edu.zsq.servicebase.common.Constants;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.page.PageDTO;
import edu.zsq.utils.page.PageData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<OrderVO> getAllOrder() {
        return lambdaQuery()
                .orderByDesc(Order::getGmtCreate)
                .list()
                .stream()
                .map(this::convert2OrderVO)
                .collect(Collectors.toList());
    }

    /**
     * 判断用户是否购买课程
     *
     * @param userId   用户id
     * @param courseId 课程id
     * @return 购买结果
     */
    @Override
    public boolean isBuyCourse(String userId, String courseId) {

        if (StringUtils.isEmpty(userId)) {
            throw ExFactory.throwBusiness("您尚未登录，请先登录");
        }

        return lambdaQuery()
                .eq(Order::getUserId, userId)
                .eq(Order::getCourseId, courseId)
                .eq(Order::getStatus, 1)
                .count() > 0;
    }

    @Override
    public String createOrder(OrderDTO orderDTO) {

        String userId = orderDTO.getUserId();

        if (StringUtils.isBlank(userId)) {
            throw ExFactory.throwBusiness("您未登录，三秒后跳转登录页面");
        }

        String orderNumber = OrderNumberUtil.getOrderNumber(orderDTO.getOrderChannelEnum(), orderDTO.getPaymentSourceEnum(), orderDTO.getOrderTypeEnum(), orderDTO.getUserId());
        orderDTO.setOrderNumber(orderNumber);

        if (!saveOrUpdate(convert2Order(orderDTO))) {
            throw ExFactory.throwSystem("系统异常, 订单创建失败");
        }

        return orderNumber;
    }

    @Override
    public OrderVO getOrderById(String orderNumber) {
        Order order = lambdaQuery()
                .eq(Order::getOrderNumber, orderNumber)
                .last(Constants.LIMIT_ONE)
                .one();
        return convert2OrderVO(order);
    }

    @Override
    public PageData<OrderVO> getOrderList(OrderQueryDTO orderQueryDTO) {

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(orderQueryDTO.getQueryKey()), orderQueryDTO.getQueryKey(), orderQueryDTO.getQueryValue());
        wrapper.lambda()
                .eq(orderQueryDTO.getStatus() != null, Order::getStatus, orderQueryDTO.getStatus())
                .orderByDesc(Order::getGmtCreate);

        IPage<Order> page = page(new Page<>(orderQueryDTO.getCurrent(), orderQueryDTO.getSize()), wrapper);

        if (page.getRecords().isEmpty()) {
            return PageData.empty();
        }
        List<OrderVO> records = page.getRecords().parallelStream().map(this::convert2OrderVO).collect(Collectors.toList());
        return PageData.of(records, page.getCurrent(), page.getSize(), page.getTotal());
    }

    private Order convert2Order(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderNumber(orderDTO.getOrderNumber());
        order.setCourseCover(orderDTO.getCourseCover());
        order.setCourseId(order.getCourseId());
        order.setCourseTitle(orderDTO.getCourseTitle());
        order.setCourseMoney(orderDTO.getCourseMoney());
        order.setMobile(orderDTO.getMobile());
        order.setNickname(orderDTO.getNickname());
        order.setPayMoney(orderDTO.getPayMoney());
        order.setTeacherName(orderDTO.getTeacherName());
        order.setPayType(orderDTO.getPaymentSourceEnum().getType());
        // 默认为未支付 等支付回调成功后修改支付状态
        order.setStatus(OrderStatusEnum.UNPAYMENT.getStatus());
        order.setReductionMoney(orderDTO.getReductionMoney());
        order.setUserId(orderDTO.getUserId());
        return order;
    }

    private OrderVO convert2OrderVO(Order order) {

        return OrderVO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .courseCover(order.getCourseCover())
                .courseId(order.getCourseId())
                .courseMoney(order.getCourseMoney())
                .courseTitle(order.getCourseTitle())
                .mobile(order.getMobile())
                .nickname(order.getNickname())
                .payMoney(order.getPayMoney())
                .reductionMoney(order.getReductionMoney())
                .status(order.getStatus())
                .teacherName(order.getTeacherName())
                .userId(order.getUserId())
                .gmtCreate(order.getGmtCreate())
                .gmtModified(order.getGmtModified())
                .payType(order.getPayType())
                .build();

    }

}
