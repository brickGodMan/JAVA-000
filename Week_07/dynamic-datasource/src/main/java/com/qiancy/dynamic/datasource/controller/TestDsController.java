package com.qiancy.dynamic.datasource.controller;

import com.qiancy.dynamic.datasource.constants.DataSourceConstants;
import com.qiancy.dynamic.datasource.entity.Order;
import com.qiancy.dynamic.datasource.services.IOrderService;
import com.qiancy.dynamic.datasource.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能简述：测试数据源
 *
 * @author qiancy
 * @create 2020/11/29
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
public class TestDsController {

    @Autowired
    private OrderService orderService;
    /**
     * 查询全部
     */
    @GetMapping("/list-order")
    @ResponseBody
    public Map<String,List<Order>> listOrder() {
        int initSize = 2;
        Map<String, List<Order>> result = new HashMap<>(initSize);
        //默认master数据源查询
        List<Order> masterOrder = orderService.getMasterOrder();
        result.put(DataSourceConstants.DS_KEY_MASTER, masterOrder);
        //从slave数据源查询
        List<Order> slaveOrder = orderService.getSlaveUser();
        result.put(DataSourceConstants.DS_KEY_SLAVE, slaveOrder);
        //返回数据
        return result;
    }
}
