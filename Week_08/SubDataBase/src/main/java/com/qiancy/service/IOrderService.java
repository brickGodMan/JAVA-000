package com.qiancy.service;

import com.qiancy.entity.Order;

import java.util.List;

/**
 * 功能简述：订单
 *
 * @author qiancy
 * @create 2020/12/9
 * @since 1.0.0
 */
public interface IOrderService{

    /**
     *  获取所有订单
     * @return
     */
    List<Order> getOrders();

    /**
     *  创建订单
     * @param order
     */
    void createOrder(Order order);

    /**
     *  修改订单
     * @param id
     */
    void updateOrder(int id);

    /**
     * 根据id 删除order
     * @param id
     */
    void deleteOrderBy(int id);
}
