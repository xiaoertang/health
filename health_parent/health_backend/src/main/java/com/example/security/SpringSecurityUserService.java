package com.example.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.pojo.Permission;
import com.example.pojo.Role;
import com.example.pojo.User;
import com.example.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 唐孝顺
 * @date 2022/3/25 16:26
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //远程调用用户服务，根据用户查询用户信息
        User user = userService.findByUsername(username);
        if(user == null){
            //不存在该用户
            return null;
        }else{
            List<GrantedAuthority> list = new ArrayList<>();
            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                //授予角色
                list.add(new SimpleGrantedAuthority(role.getKeyword()));
                Set<Permission> permissions = role.getPermissions();
                for (Permission permission : permissions) {
                    //授权
                    list.add(new SimpleGrantedAuthority(permission.getKeyword()));
                }
            }
            org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(username, user.getPassword(), list);
            return securityUser;
        }
    }
}
