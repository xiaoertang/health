package com.example.service;

import com.example.pojo.CheckGroup;

/**
 * @author 唐孝顺
 * @date 2022/3/14 13:13
 * 检查组服务接口
 */
public interface CheckGroupService {
    public void add(CheckGroup checkGroup, Integer[] checkitemIds);
}
