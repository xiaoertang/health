package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.CheckGroupDao;
import com.example.entity.PageResult;
import com.example.pojo.CheckGroup;
import com.example.service.CheckGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
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
        this.setCheckGroupAndCheckItem(checkGroup, checkitemIds);

    }


    //分页
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        checkGroupDao.deleteAssociation(id);
        checkGroupDao.deleteById(id);

//        if(count == 0){
//            throw new RuntimeException("当前检查组被引用，不能删除！");
//        }
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更新检查组基本信息
        checkGroupDao.edit(checkGroup);
        //编辑检查组，同时需要更新和检查项的关联关系
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //向中间表（t_checkgroup_checkitem）插入数据（建立关系）
        this.setCheckGroupAndCheckItem(checkGroup, checkitemIds);

    }

    //设置检查组合和检查项的关联关系
    public void setCheckGroupAndCheckItem(CheckGroup checkGroup, Integer[] checkitemIds) {
        if (checkitemIds != null && checkitemIds.length > 0) {
            Integer checkGroupId = checkGroup.getId();
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkgroup_id", checkGroupId);
                map.put("checkitem_id", checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

    //查询所有
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
