package com.example.service;

import com.example.entity.PageResult;
import com.example.pojo.CheckGroup;
import com.example.pojo.Setmeal;

import java.util.List;

/**
 * @author 唐孝顺
 * @date 2022/3/15 14:51
 */
public interface SetMealService {
    //新增
    public void add(Setmeal setmeal, Integer[] checkgroupIds);
    //分页
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
    //查找所有
    public List<Setmeal> findAll();
    //根据id查询
    public Setmeal findById(Integer id);
}
