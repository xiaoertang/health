package com.example.dao;

import com.example.pojo.CheckGroup;
import com.example.service.CheckGroupService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author 唐孝顺
 * @date 2022/3/14 13:22
 */
public interface CheckGroupDao {
    public void add(CheckGroup checkGroup);
    public void setCheckGroupAndCheckItem(Map map);

    //分页
    public Page<CheckGroup> selectByCondition(String queryString);

    //删除
    public void deleteById(Integer id);
    //删除映射关系
    public void deleteCheckGroupAndCheckItemByCheckGroupById(Integer checkgroup_id);
    //根据id插叙
    public CheckGroup findById(Integer id);

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    public void deleteAssociation(Integer id);
    public void edit(CheckGroup checkGroup);

    //查询所有
    public List<CheckGroup> findAll();


}
