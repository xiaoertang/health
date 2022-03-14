package com.example.dao;

import com.example.pojo.CheckItem;
import com.github.pagehelper.Page;


/**
 * @author 唐孝顺
 * @date 2022/3/13 14:13
 */
public interface CheckItemDao {
    public void add(CheckItem checkItem);
    public Page<CheckItem> selectByCondition(String queryString);
    public long findCountByCheckItemId(Integer id);
    public void deleteById(Integer id);
    public void edit(CheckItem checkItem);
    public CheckItem findById(Integer id);
}
