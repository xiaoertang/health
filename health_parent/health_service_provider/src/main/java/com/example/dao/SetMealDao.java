package com.example.dao;

import com.example.pojo.CheckGroup;
import com.example.pojo.Setmeal;
import com.example.service.SetMealService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 唐孝顺
 * @date 2022/3/15 14:52
 */
public interface SetMealDao {
    //新增
    public void add(Setmeal setmeal);
    public void setSetMealAndCheckGroup(Map map);
    //分页
    public Page<Setmeal> selectByCondition(String queryString);
    //查询所有
    public List<Setmeal> findAll();
    //根据id查询
    public Setmeal findById(Integer id);
    //根据id查询
    public Setmeal findById2(Integer id);
}
