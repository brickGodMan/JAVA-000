package com.qiancy.dynamic.datasource.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiancy.dynamic.datasource.annotation.DS;
import com.qiancy.dynamic.datasource.constants.DataSourceConstants;
import com.qiancy.dynamic.datasource.entity.Order;

import java.util.List;

/**
 * 功能简述：订单service
 *
 * @author qiancy
 * @create 2020/11/29
 * @since 1.0.0
 */
public interface IOrderService extends IService<Order> {

    @DS(value = DataSourceConstants.DS_KEY_MASTER)
    List<Order> getMasterOrder();

    @DS(value = DataSourceConstants.DS_KEY_SLAVE)
    List<Order> getSlaveUser();
}
