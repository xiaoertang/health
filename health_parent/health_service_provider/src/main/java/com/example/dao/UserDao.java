package com.example.dao;

import com.example.pojo.User;

/**
 * @author 唐孝顺
 * @date 2022/3/25 16:46
 */
public interface UserDao {
    public User findByUsername(String username);
}
