package com.example.dao;

import com.example.pojo.CheckGroup;
import com.example.service.CheckGroupService;

import java.util.Map;

/**
 * @author 唐孝顺
 * @date 2022/3/14 13:22
 */
public interface CheckGroupDao {
    public void add(CheckGroup checkGroup);
    public void setCheckGroupAndCheckItem(Map map);
}
