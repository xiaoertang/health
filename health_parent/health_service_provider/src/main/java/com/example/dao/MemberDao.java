package com.example.dao;

import com.example.pojo.Member;

/**
 * @author 唐孝顺
 * @date 2022/3/22 14:30
 */
public interface MemberDao {
    //根据手机号查询
    public Member findByTelephone(String telephone);
    //新增
    public void add(Member member);
}
