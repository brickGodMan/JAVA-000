package com.qiancy.dubbo.demo.account.service;

import com.qiancy.dubbo.demo.common.account.api.AccountService;
import org.springframework.stereotype.Service;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/12/28
 * @since 1.0.0
 */
@Service
public class AccountServiceImpl implements AccountService {
    public String test() {
        return "hello dubbo";
    }
}
