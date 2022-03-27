package com.example.dao;

import com.example.pojo.Role;
import com.example.pojo.User;

import java.util.Set;

/**
 * @author 唐孝顺
 * @date 2022/3/25 16:46
 */
public interface RoleDao {
    public Set<Role> findByUserId(Integer userId);
}
