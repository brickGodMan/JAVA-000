package io.qiancy.rpcfx.demo.provider;

import io.qiancy.rpcfx.demo.api.User;
import io.qiancy.rpcfx.demo.api.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        return new User(id, "KK" + System.currentTimeMillis());
    }
}
