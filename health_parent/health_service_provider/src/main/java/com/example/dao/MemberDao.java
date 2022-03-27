package com.example.dao;

import com.example.pojo.Member;

/**
 * @author 唐孝顺
 * @date 2022/3/22 14:30
 */
public interface MemberDao {
    //根据邮箱地址查询
    public Member findByEmail(String email);
    //新增
    public void add(Member member);
    //根据id查询
    public Member findById(Integer id);
    //根据当前月查询之前月份的个数
    public Integer findMemberCountBeforeDate(String date);
}
