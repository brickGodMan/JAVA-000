package io.qiancy.rpcfx.demo.provider;

import io.qiancy.rpcfx.demo.api.Order;
import io.qiancy.rpcfx.demo.api.OrderService;

public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(int id) {
        return new Order(id, "Cuijing" + System.currentTimeMillis(), 9.9f);
    }
}
