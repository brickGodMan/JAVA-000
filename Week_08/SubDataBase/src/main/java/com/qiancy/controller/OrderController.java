package com.qiancy.controller;

import com.qiancy.entity.Order;
import com.qiancy.service.IOrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能简述：订单
 *
 * @author qiancy
 * @create 2020/12/9
 * @since 1.0.0
 */
@RestController
public class OrderController {
    @Autowired
    private IOrderService orderService;
    /**
     * 查询全部
     */
    @GetMapping("/list-order")
    @ResponseBody
    public Map<String, List<Order>> listOrder() {
        int initSize = 8;
        Map<String, List<Order>> result = new HashMap<>(initSize);
        //默认master数据源查询
        List<Order> masterOrder = orderService.getOrders();
        result.put("orders", masterOrder);
        //返回数据
        return result;
    }
    /**
     * 创建订单
     */
    @GetMapping("/create-order")
    @ResponseBody
    public String createOrder() {
        int initSize = 8;
        Map<String, List<Order>> result = new HashMap<>(initSize);
        Order order = new Order();
        order.setId("2");
        order.setUserId("2");
        order.setAmount("100");
        order.setStatus("1");
        order.setCreateTime(Date.valueOf(LocalDateTime.now().toLocalDate()));
        //默认master数据源查询
        orderService.createOrder(order);
        //返回数据
        return "success";
    }

}
