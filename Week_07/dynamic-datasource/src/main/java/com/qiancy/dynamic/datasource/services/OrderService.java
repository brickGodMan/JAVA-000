package com.qiancy.dynamic.datasource.services;

import com.qiancy.dynamic.datasource.annotation.DS;
import com.qiancy.dynamic.datasource.constants.DataSourceConstants;
import com.qiancy.dynamic.datasource.entity.Order;
import com.qiancy.dynamic.datasource.mapper.master.OrderMapperMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/29
 * @since 1.0.0
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapperMaster master;

    @Autowired
    private com.qiancy.dynamic.datasource.mapper.slave.OrderMapperSlave slave;

    public List<Order> getMasterOrder() {
        System.out.println("查询主库");

        return master.selectAll(new Order());
    }

    public List<Order> getSlaveUser() {
        System.out.println("查询从库");
        return slave.selectAll(null);
    }
}
