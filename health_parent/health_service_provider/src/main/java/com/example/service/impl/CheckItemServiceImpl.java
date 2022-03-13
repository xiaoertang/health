package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.CheckItemDao;
import com.example.pojo.CheckItem;
import com.example.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 唐孝顺
 * @date 2022/3/13 14:11
 * 检查项服务
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    //注入DAO对象
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }
}
