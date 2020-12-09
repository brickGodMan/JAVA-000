package com.qiancy.service.impl;

import com.qiancy.entity.Order;
import com.qiancy.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/12/9
 * @since 1.0.0
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Order> getOrders() {
        String sql = "select * from order_t ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(
                Order.class));
    }

    @Override
    public void createOrder(Order order) {
        StringBuffer sb = new StringBuffer();
        sb.append("insert into order_t(id, user_id, amount, status, create_time)");
        sb.append("values(");
        sb.append("'" + order.getId()).append("',");
        sb.append("'" + order.getId()).append("',");
        sb.append("'" + order.getAmount()).append("',");
        sb.append("'" + order.getStatus()).append("',");
        sb.append("'" + order.getCreateTime()).append("'");
        sb.append(")");
        jdbcTemplate.update(sb.toString());
    }

    @Override
    public void updateOrder(Order order) {
        StringBuffer sb = new StringBuffer();
        sb.append("update order_t set status = )");
        sb.append("where id = '").append(order.getId() + "'");
        jdbcTemplate.update(sb.toString());
    }

}
