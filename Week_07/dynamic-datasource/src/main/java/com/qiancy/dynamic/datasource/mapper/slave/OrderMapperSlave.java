package com.qiancy.dynamic.datasource.mapper.slave;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiancy.dynamic.datasource.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qiancy
 */
@Repository
public interface OrderMapperSlave extends BaseMapper<Order> {

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectAll(Order order);
}