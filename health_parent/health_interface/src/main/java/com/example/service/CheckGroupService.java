package com.example.service;

import com.example.entity.PageResult;
import com.example.pojo.CheckGroup;

import java.util.List;

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
    //根据id查询
    public CheckGroup findById(Integer id);
    //根据检查组id查询对应的所有的检查项id
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    //编辑
    public void edit(CheckGroup checkGroup,Integer[] checkitemIds);
    //查询所有
    public List<CheckGroup> findAll();
}
