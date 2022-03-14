package com.example.service;

import com.example.entity.PageResult;
import com.example.pojo.CheckGroup;

/**
 * @author 唐孝顺
 * @date 2022/3/14 13:13
 * 检查组服务接口
 */
public interface CheckGroupService {
    //新增
    public void add(CheckGroup checkGroup, Integer[] checkitemIds);
    //分页
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
    //删除
    public void deleteById(Integer id);
}
