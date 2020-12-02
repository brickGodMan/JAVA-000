package qiancy.sharding.services;

import qiancy.sharding.entity.Order;

import java.util.List;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/12/2
 * @since 1.0.0
 */
public interface IOrderService {

    List<Order> getOrderListByUserId(Integer userId);

    List<Order> getOrderList();

    void createOrder(Order order);
}
