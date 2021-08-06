package edu.zsq.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.order.entity.Order;
import edu.zsq.order.entity.dto.OrderDTO;
import edu.zsq.order.entity.dto.OrderQueryDTO;
import edu.zsq.service_order_api.entity.OrderVO;
import edu.zsq.utils.page.PageData;

import java.util.List;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
public interface OrderService extends IService<Order> {

    /**
     * 获取全部订单
     *
     * @return 订单列表
     */
    List<OrderVO> getAllOrder();

    /**
     * 判断用户是否购买课程
     *
     * @param userId   用户id
     * @param courseId 课程id
     * @return 是否购买
     */

    boolean isBuyCourse(String userId, String courseId);

    /**
     * 生成订单
     *
     * @param orderDTO 订单信息
     * @return 订单号
     */
    String createOrder(OrderDTO orderDTO);

    /**
     * 获取单个订单信息
     *
     * @param orderNumber 订单号
     * @return 订单信息
     */
    OrderVO getOrderById(String orderNumber);

    /**
     * 分页获取订单列表
     *
     * @param orderQueryDTO 查询条件
     * @return 订单列表
     */
    PageData<OrderVO> getOrderList(OrderQueryDTO orderQueryDTO);

    /**
     * 删除订单
     *
     * @param orderNumber 订单号
     */
    void removeOrder(String orderNumber);
}
