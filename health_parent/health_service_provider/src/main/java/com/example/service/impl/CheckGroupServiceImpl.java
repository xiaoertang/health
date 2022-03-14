package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.CheckGroupDao;
import com.example.pojo.CheckGroup;
import com.example.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 唐孝顺
 * @date 2022/3/14 13:15
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    //新增检查组
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        if (checkitemIds != null && checkitemIds.length > 0) {
            Integer checkGroupId = checkGroup.getId();
            setCheckGroupAndCheckItem(checkGroupId, checkitemIds);
        }
    }

    //设置检查组合和检查项的关联关系
    public void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        for (Integer checkitemId : checkitemIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put("checkgroup_id", checkGroupId);
            map.put("checkitem_id", checkitemId);
            checkGroupDao.setCheckGroupAndCheckItem(map);
        }
    }
}
