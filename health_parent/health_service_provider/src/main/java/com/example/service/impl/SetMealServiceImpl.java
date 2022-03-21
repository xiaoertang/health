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
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}")//从属性文件读取输出目录的路径
    private String outputpath;

    //新增套餐
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setMealDao.add(setmeal);
        this.setSetMealAndCheckGroup(setmeal, checkgroupIds);
        this.savePic2Redis(setmeal.getImg());
        //新增套餐后需要重新生成静态页面
        this.generateMobileStaticHtml();
    }

    //将图片名称保存到Redis
    private void savePic2Redis(String pic) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, pic);
        //jedisPool.close();
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

    //生成静态页面
    public void generateMobileStaticHtml() {
        //准备模板数据
        List<Setmeal> setmealList = this.findAll();
        //生成套餐列表静态页面
        this.generateMobleSetmealListHtml(setmealList);
        //生成套餐详细静态页面
        this.generateMobleSetmealDetailHtml(setmealList);
    }
    //生成套餐列表静态页面
    public void generateMobleSetmealListHtml(List<Setmeal> setmealList){
      Map<String, Object> dataMap = new HashMap<String, Object>();
      dataMap.put("setmealList",setmealList);
      this.generateHtml("mobile_setmeal.ftl","m_setmeal", dataMap);
    }
    //生成套餐详细静态页面（多个）
    public void generateMobleSetmealDetailHtml(List<Setmeal> setmealList) {
        for (Setmeal setmeal : setmealList) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("setmeal",this.findById(setmeal.getId()));
            this.generateHtml("mobile_setmeal_detail.ftl",
                    "setmeal_detail_" + setmeal.getId(),
                    dataMap);
        }
    }

    public void generateHtml(String temlateName, String htmlPageName, Map<String, Object> dataMap) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try {
            //加载模板文件
            Template template = configuration.getTemplate(temlateName);
            //生成数据
            File docFile = new File(outputpath + "\\" + htmlPageName + ".html");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            //输出文件
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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

    @Override
    public List<Setmeal> findAll() {
        return setMealDao.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }
}
