package com.example.service;

import com.example.entity.PageResult;
import com.example.pojo.CheckGroup;
import com.example.pojo.Setmeal;

/**
 * @author 唐孝顺
 * @date 2022/3/15 14:51
 */
public interface SetMealService {
    //新增
    public void add(Setmeal setmeal, Integer[] checkgroupIds);
    //分页
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
}
