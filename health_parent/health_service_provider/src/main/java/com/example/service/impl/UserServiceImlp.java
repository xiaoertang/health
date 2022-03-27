package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.PermissionDao;
import com.example.dao.RoleDao;
import com.example.dao.UserDao;
import com.example.pojo.Permission;
import com.example.pojo.Role;
import com.example.pojo.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author 唐孝顺
 * @date 2022/3/25 16:45
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImlp implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);//查询用户基本信息
        if (user == null) {
            return null;
        }
        Integer userId = user.getId();
        //根据用户Id查询角色信息
        Set<Role> roles = roleDao.findByUserId(userId);
        if (roles != null) {
            for (Role role : roles) {
                Integer roleId = role.getId();
                //根据角色Id查询权限信息
                Set<Permission> permissions = permissionDao.findByRoleId(roleId);
                if (permissions != null && permissions.size() > 0) {
                    role.setPermissions(permissions);
                }
            }
            user.setRoles(roles);
        }
        return user;
    }
}
