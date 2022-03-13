package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.constant.MessageConstant;
import com.example.entity.Result;
import com.example.pojo.CheckItem;
import com.example.service.CheckItemService;

import org.springframework.web.bind.annotation.*;


/**
 * @author 唐孝顺
 * @date 2022/3/13 13:51
 * 检查项管理
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference //查找服务
    private CheckItemService checkItemService;

    //新增检查项
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
}
