package com.example.service;

import com.example.pojo.Member;

import java.util.List;

/**
 * @author 唐孝顺
 * @date 2022/3/24 14:28
 */
public interface MemberService {
    public void add(Member member);
    public Member findByEmail(String email);
    public List<Integer> findMemberByMonth(List<String> months);
}
