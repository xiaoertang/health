package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.constant.RedisConstant;
import com.example.dao.SetMealDao;
import com.example.entity.PageResult;
import com.example.pojo.CheckGroup;
import com.example.pojo.Setmeal;
import com.example.service.SetMealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 唐孝顺
 * @date 2022/3/15 14:52
 */
@Service(interfaceClass = SetMealService.class)
@Transactional
@SuppressWarnings({"all"})
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealDao setMealDao;
    @Autowired
    private JedisPool jedisPool;
    //新增套餐
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
            setMealDao.add(setmeal);
            this.setSetMealAndCheckGroup(setmeal,checkgroupIds);
            this.savePic2Redis(setmeal.getImg());
    }
    //将图片名称保存到Redis
    private void savePic2Redis(String pic){
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
    }

    //设置套餐和检查组合的关联关系
    public void setSetMealAndCheckGroup(Setmeal setmeal, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            Integer setmealId = setmeal.getId();
            for (Integer checkgroupId : checkgroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("setmeal_id", setmealId);
                map.put("checkgroup_id", checkgroupId);
                setMealDao.setSetMealAndCheckGroup(map);
            }
        }
    }

    //分页
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> page = setMealDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
