package qiancy.sharding.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import qiancy.sharding.entity.Order;
import qiancy.sharding.services.IOrderService;

import java.util.List;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/12/2
 * @since 1.0.0
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Order> getOrderListByUserId(Integer userId) {
        return null;
    }

    @Override
    public List<Order> getOrderList() {
        String sql = "select * from order_t ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(
                Order.class));
    }

    @Override
    public void createOrder(Order order) {
        StringBuffer sb = new StringBuffer();
        sb.append("insert into order_t(id, user_id)");
        sb.append("values(");
        sb.append("'" + order.getUserId()).append("',");
        sb.append("'" + order.getId());
        sb.append("')");
        jdbcTemplate.update(sb.toString());
    }
}
