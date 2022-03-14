package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.constant.MessageConstant;
import com.example.entity.Result;
import com.example.pojo.CheckGroup;
import com.example.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;


/**
 * @author 唐孝顺
 * @date 2022/3/14 13:08
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    //新增检查组
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.add(checkGroup, checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            //新增失败
            return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        //新增成功
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
}
