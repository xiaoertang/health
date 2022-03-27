package com.example.service;

import com.example.pojo.User;

/**
 * @author 唐孝顺
 * @date 2022/3/25 16:31
 */
public interface UserService {
    public User findByUsername(String username);
}
